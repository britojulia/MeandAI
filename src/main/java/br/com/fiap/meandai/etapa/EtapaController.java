package br.com.fiap.meandai.etapa;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.trilha.TrilhaService;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;
    private final MessageHelper messageHelper;
    private final UserService userService;


    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        var etapas = etapaService.getAllEtapas();
        model.addAttribute("etapas", etapas);
        return "index";
    }

    @PutMapping("/concluir/{id}")
    public String concluirEtapa(@PathVariable Long id,
                                @AuthenticationPrincipal OAuth2User principal) {
        Etapa etapa = etapaService.concluir(id, userService.register(principal));
        return "redirect:/trilha/" + etapa.getTrilha().getId();
    }

    @PutMapping("/desfazer/{id}")
    public String desfazer(@PathVariable Long id,
                           RedirectAttributes redirect,
                           @AuthenticationPrincipal OAuth2User principal) {
        Etapa etapa = etapaService.naoConcluida(id, userService.register(principal));
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/trilha/" + etapa.getTrilha().getId();
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        etapaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/evento";
    }

}
