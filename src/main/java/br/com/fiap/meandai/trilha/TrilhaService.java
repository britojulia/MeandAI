package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.etapa.EtapaRepository;
import br.com.fiap.meandai.messaging.MessagePublisher;
import br.com.fiap.meandai.skill.Skill;
import br.com.fiap.meandai.skill.SkillRepository;
import br.com.fiap.meandai.skill.SkillService;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;
    private final UserRepository userRepository;
    private final SkillService skillService;
    private final MessagePublisher publisher;
    private final ChatClient chatClient;


    public record ResultadoIA(String conteudo) {}


    @Cacheable(value = "trilhas")
    public List<Trilha> getAllTrilhas(){
        return trilhaRepository.findAll();
    }

    public ResultadoIA gerarEtapasComIA(User user) {

        // Pega todas as skills do usuário
        List<Skill> skills = skillService.findByUserId(user.getId());

        // Monta a string "Java (Intermediário), SQL (Avançado)"
        String skillsTexto = skills.stream()
                .map(s -> s.getNome() + " (" + s.getNivel() + ")")
                .collect(Collectors.joining(", "));

        String prompt = """
        Gere uma trilha personalizada de aprendizado com base nas habilidades do usuário abaixo e o objetivo de carreira dele.
        Retorne apenas o texto final, sem formatação JSON e sem MARKDOWN.

        Usuário: %s
        Habilidades: %s
        Objetivo: %s
        """.formatted(user.getName(), skillsTexto, user.getObjetivo());

        String respostaIA = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new ResultadoIA(respostaIA);
    }




//    public List<String> gerarEtapasComIA(User user) {
//        String prompt = """
//                Crie uma trilha de aprendizado PARA ESTE USUÁRIO:
//
//                Área atual: %s
//                Objetivo profissional: %s
//                Skills que ele já possui: %s
//
//                Gere uma lista de 5 a 8 etapas.
//                Responda APENAS com os nomes das etapas, uma por linha.
//                """.formatted(
//                user.getAreaAtual(),
//                user.getObjetivo(),
//                user.getTrilhas()
//        );
//
//        String resposta = chatClient
//                .prompt()
//                .user(prompt)
//                .call()
//                .content();
//
//        return resposta.lines()
//                .map(String::trim)
//                .filter(s -> !s.isEmpty())
//                .toList();
//    }

//    public Trilha createTrilha(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
//
//        Trilha trilha = Trilha.builder()
//                .titulo("Trilha Personalizada")
//                .descricao("Trilha criada automaticamente pela IA")
//                .dataCriacao(LocalDate.now())
//                .user(user)
//                .build();
//
//        trilha = trilhaRepository.save(trilha);
//        List<String> etapasGeradas = gerarEtapasComIA(user);
//
//        for (String nome : etapasGeradas) {
//            Etapa etapa = Etapa.builder()
//                    .nome(nome)
//                    .descricao("Etapa sugerida pela IA")
//                    .concluida(false)
//                    .trilha(trilha)
//                    .build();
//            etapaRepository.save(etapa);
//        }
//
//        publisher.sendTrilhaCreated(trilha);
//        return trilha;
//    }

    public ResultadoIA createTrilha(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // chama a IA
        ResultadoIA resultado = gerarEtapasComIA(user);

        // cria a trilha normal
        Trilha trilha = Trilha.builder()
                .titulo("Trilha Personalizada")
                .descricao("Trilha criada automaticamente pela IA")
                .dataCriacao(LocalDate.now())
                .user(user)
                .build();

        trilha = trilhaRepository.save(trilha);

        publisher.sendTrilhaCreated(trilha);
        return resultado;
    }



    public void deleteById(Long id){
        trilhaRepository.delete(getTrilhaById(id));
    }

    public Trilha getTrilhaById(Long id){
        return trilhaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("trilha não encontrado")
        );
    }


}
