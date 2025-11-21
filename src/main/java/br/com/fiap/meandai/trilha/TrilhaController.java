package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.etapa.EtapaService;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trilha")
@RequiredArgsConstructor
public class TrilhaController {

    private final MessageHelper messageHelper;
    private final TrilhaService trilhaService;
    private final TrilhaRepository trilhaRepository;
    private final UserService userService;
    private final EtapaService etapaService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        var trilhas = trilhaService.getAllTrilhas();

        Map<Long, Long> etapasConcluidas = etapaService.contarEtapasConcluidasPorTrilha();
        Map<Long, Long> etapasTotais = etapaService.contarEtapasTotaisPorTrilha();

        model.addAttribute("trilhas", trilhas);
        model.addAttribute("etapasConcluidas", etapasConcluidas);
        model.addAttribute("etapasTotais", etapasTotais);
        model.addAttribute("user", user);

        return "index";
    }


    //lista completa de trilhas
    @GetMapping("/lista")
    public String listarTrilhas(Model model,
                                @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        List<Trilha> trilhas = trilhaRepository.findAll();


        Map<Long, Long> etapasConcluidas = etapaService.contarEtapasConcluidasPorTrilha();
        Map<Long, Long> etapasTotais = etapaService.contarEtapasTotaisPorTrilha();

        model.addAttribute("trilhas", trilhas);
        model.addAttribute("etapasConcluidas", etapasConcluidas);
        model.addAttribute("etapasTotais", etapasTotais);
        model.addAttribute("user", user);
        return "trilhas-lista";
    }

    //gerar a trilha dinâmica  sem salvar
    @GetMapping("/gerar")
    public String gerarTrilhaDinamica(Model model,
                                      @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        var resultado = trilhaService.gerarEtapasComIA(user);

        model.addAttribute("respostaIA", resultado.conteudo());
        model.addAttribute("user", user);

        return "trilha-gerada"; // mostra só o texto gerado pela IA
    }

    //salva a trilha com as etapas separadas
    @PostMapping()
    public String criarTrilha(Model model,
                              RedirectAttributes redirect,
                              @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        Trilha trilha = trilhaService.createTrilha(user.getId());

        // adiciona a trilha no model para exibir na página
        model.addAttribute("trilha", trilha);
        model.addAttribute("user", user);
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));

        return "redirect:/trilha/" + trilha.getId();
    }

    @GetMapping("/{id}")
    public String detalhesTrilha(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        Trilha trilha = trilhaService.getTrilhaById(id);
        model.addAttribute("trilha", trilha);
        model.addAttribute("user", user);
        return "trilha-detalhes";
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        trilhaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/trilha/lista";
    }


}
