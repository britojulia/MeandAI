package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.trilha.TrilhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;
    private final MessageHelper messageHelper;


    @GetMapping
    public String index(Model model) {
        var etapas = etapaService.getAllEtapas();
        model.addAttribute("etapas", etapas);
        return "index";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        etapaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.delete.success"));
        return "redirect:/evento";
    }

}
