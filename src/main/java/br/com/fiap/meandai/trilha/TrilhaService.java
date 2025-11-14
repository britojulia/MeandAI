package br.com.fiap.meandai.trilha;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrilhaService {

    private TrilhaService  trilhaService;

    public TrilhaService(TrilhaRepository trilhaRepository) {
        this.trilhaService = trilhaService;
    }

    public List<Trilha> getAllTrilhas(){
        return trilhaService.getAllTrilhas();
    }


}
