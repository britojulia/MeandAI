package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.etapa.EtapaRepository;
import br.com.fiap.meandai.messaging.MessagePublisher;
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

@Service
@RequiredArgsConstructor
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;
    private final UserRepository userRepository;
    private final EtapaRepository etapaRepository;
    private final MessagePublisher publisher;
    private final ChatClient chatClient;

    @Cacheable(value = "trilhas")
    public List<Trilha> getAllTrilhas(){
        return trilhaRepository.findAll();
    }

    public Trilha save(Trilha trilha){
        return trilhaRepository.save(trilha);
    }

    public List<String> gerarEtapasComIA(User user) {
        String prompt = """
        Crie uma trilha de aprendizado PARA ESTE USUÁRIO:

        Área atual: %s
        Objetivo profissional: %s
        Skills que ele já possui: %s

        Gere uma lista de 5 a 8 etapas.
        Responda APENAS com os nomes das etapas, uma por linha.
        """.formatted(
                user.getAreaAtual(),
                user.getObjetivo(),
                user.getTrilhas()
        );

        String resposta = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        return resposta.lines()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public Trilha createTrilha(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Trilha trilha = Trilha.builder()
                .titulo("Trilha Personalizada")
                .descricao("Trilha criada automaticamente pela IA")
                .dataCriacao(LocalDate.now())
                .user(user)
                .build();

        trilha = trilhaRepository.save(trilha);
        List<String> etapasGeradas = gerarEtapasComIA(user);

        for (String nome : etapasGeradas) {
            Etapa etapa = Etapa.builder()
                    .nome(nome)
                    .descricao("Etapa sugerida pela IA")
                    .concluida(false)
                    .trilha(trilha)
                    .build();
            etapaRepository.save(etapa);
        }

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
