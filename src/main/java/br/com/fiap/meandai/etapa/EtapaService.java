package br.com.fiap.meandai.etapa;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EtapaService {
    
    private final EtapaRepository etapaRepository;
    
    public EtapaService(EtapaRepository etapaRepository) {
        this.etapaRepository = etapaRepository;
    }

    @Cacheable(value = "etapas")
    public List<Etapa> getAllEtapas() {
        return etapaRepository.findAll();
    }

    public void deleteById(Long id){
        etapaRepository.delete(getEtapaById(id));
    }

    public Etapa concluir(Long id) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etapa não encontrada"));
        etapa.setConcluida(true);
        return etapaRepository.save(etapa);
    }

    public Etapa naoConcluida(Long id) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etapa não encontrada"));
        etapa.setConcluida(false);
        return etapaRepository.save(etapa);
    }

    public Map<Long, Long> contarEtapasConcluidasPorTrilha() {
        return etapaRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTrilha().getId(),
                        Collectors.filtering(Etapa::isConcluida, Collectors.counting())
                ));
    }

    public Map<Long, Long> contarEtapasTotaisPorTrilha() {
        return etapaRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTrilha().getId(),
                        Collectors.counting()
                ));
    }

    private Etapa getEtapaById(Long id){
        return etapaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("etapa não encontrado")
        );
    }
}
