package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.trilha.TrilhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;

    @GetMapping
    public String index(Model model) {
        var etapas = etapaService.getAllEtapas();
        model.addAttribute("etapas", etapas);
        return "index";
    }
}
