package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.trilha.TrilhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;
    private final EtapaRepository etapaRepository;
    private final MessageHelper messageHelper;


    @GetMapping
    public String index(Model model) {
        var etapas = etapaService.getAllEtapas();
        model.addAttribute("etapas", etapas);
        return "index";
    }

    @PutMapping("/concluir/{id}")
    public String concluirEtapa(@PathVariable Long id) {
        Etapa etapa = etapaService.concluir(id);
        return "redirect:/trilha/" + etapa.getTrilha().getId();
    }

    @PutMapping("/desfazer/{id}")
    public String desfazer(@PathVariable Long id, RedirectAttributes redirect) {
        Etapa etapa = etapaService.naoConcluida(id);
        redirect.addFlashAttribute("message", "Etapa reaberta!");
        return "redirect:/trilha/" + etapa.getTrilha().getId();
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        etapaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.delete.success"));
        return "redirect:/evento";
    }

}
