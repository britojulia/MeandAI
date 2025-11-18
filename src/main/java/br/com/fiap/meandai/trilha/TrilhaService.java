package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.etapa.EtapaRepository;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    @Cacheable(value = "trilhas")
    public List<Trilha> getAllTrilhas(){
        return trilhaRepository.findAll();
    }

    public Trilha save(Trilha trilha){
        return trilhaRepository.save(trilha);
    }

    public Trilha createTrilha(Trilha trilha, List<String> etapasForm, Long userId){
        var user = userRepository.findById(userId).orElseThrow();
        trilha.setUser(user);
        trilha.setDataCriacao(LocalDate.now());

        trilha = trilhaRepository.save(trilha);

        //dados mocados
        for (String nome : etapasForm) {
            Etapa etapa = Etapa.builder()
                    .nome(nome)
                    .descricao("Etapa da trilha")
                    .concluida(false)
                    .trilha(trilha)
                    .build();

            etapaRepository.save(etapa);
        }
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
