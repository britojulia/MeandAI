package br.com.fiap.meandai.etapa;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtapaService {
    
    private final EtapaRepository etapaRepository;
    
    public EtapaService(EtapaRepository etapaRepository) {
        this.etapaRepository = etapaRepository;
    }
    
    public List<Etapa> getAllEtapas() {
        return etapaRepository.findAll();
    }
}
