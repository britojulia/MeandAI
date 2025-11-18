package br.com.fiap.meandai.etapa;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private Etapa getEtapaById(Long id){
        return etapaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("etapa não encontrado")
        );
    }
}
