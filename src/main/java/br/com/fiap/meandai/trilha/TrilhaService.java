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
import java.util.ArrayList;
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
        Nessa trilha, você deve dar instruções claras sobre o passo a passo, e separar por etapas.
        Retorne apenas o texto final, sem formatação JSON e sem caracteres * #.
        A primeira linha da trilha deverá ser o título da trilha.

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


    public Trilha createTrilha(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // chama a IA
        ResultadoIA resultado = gerarEtapasComIA(user);
        String conteudo = resultado.conteudo();

        // Pega o título da trilha (linha antes da primeira quebra de linha)
        String titulo = conteudo.lines().findFirst().orElse("Trilha Personalizada");

        // cria a trilha
        Trilha trilha = Trilha.builder()
                .titulo(titulo)
                .descricao("Trilha criada automaticamente pela IA")
                .dataCriacao(LocalDate.now())
                .conteudoGeradoIA(resultado.conteudo())
                .user(user)
                .build();

        trilha = trilhaRepository.save(trilha);

        // Divide o conteúdo em etapas
        List<Etapa> etapas = new ArrayList<>();
        String[] partes = conteudo.split("Etapa \\d+:"); // separa cada etapa
        for (String parte : partes) {
            parte = parte.trim();
            if (parte.isEmpty()) continue;

            // Pega o objetivo da etapa (primeira linha) e usa como descrição
            String[] linhas = parte.split("\n", 2);
            String objetivo = linhas[0].trim(); // primeira linha
            String descricao = linhas.length > 1 ? linhas[1].trim() : "";

            // Salva nome curto (Etapa 1, Etapa 2...) e descrição resumida
            Etapa etapa = Etapa.builder()
                    .nome(objetivo.length() > 50 ? objetivo.substring(0, 50) : objetivo) // limita o nome
                    .descricao(descricao.length() > 255 ? descricao.substring(0, 255) : descricao)
                    .concluida(false)
                    .trilha(trilha)
                    .build();

            etapas.add(etapa);
        }

        trilha.setEtapas(etapas);
        trilhaRepository.save(trilha); // CascadeType.ALL salva as etapas também

        publisher.sendTrilhaCreated(trilha);
        return trilha;
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
