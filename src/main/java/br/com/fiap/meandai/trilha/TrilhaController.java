package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.etapa.Etapa;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/trilha")
@RequiredArgsConstructor
public class TrilhaController {

    private final MessageHelper messageHelper;
    private final TrilhaService trilhaService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        var trilhas = trilhaService.getAllTrilhas();
        model.addAttribute("trilhas", trilhas);
        return "index";
    }

    @PostMapping("/{userId}")
    public TrilhaService.ResultadoIA criarTrilha(@PathVariable Long userId) {
        return trilhaService.createTrilha(userId);
    }

    @GetMapping("/gerar/{userId}")
    public String gerarTrilhaDinamica(@PathVariable Long userId, Model model) {

        User user = userService.getUserById(userId);

        var resultado = trilhaService.gerarEtapasComIA(user);
        model.addAttribute("respostaIA", resultado.conteudo());
        model.addAttribute("user", user);

        return "trilha-gerada";
    }


//    @GetMapping("/criar/{userId}")
//    public String criarAutomaticamente(@PathVariable Long userId,
//                                       RedirectAttributes redirect) {
//
//        var trilha = trilhaService.createTrilha(userId);
//
//        redirect.addFlashAttribute("message",
//                messageHelper.get("message.success"));
//
//        return "redirect:/trilha/" + trilha.getId();
//    }
//
//    @GetMapping("/gerar/{userId}")
//    public String gerarTrilhaDinamica(@PathVariable Long userId, Model model) {
//
//        // Busca o usuário
//        User user = trilhaService.getUserById(userId);
//
//        // Gera etapas dinamicamente usando IA (sem salvar no banco)
//        List<String> etapasGeradas = trilhaService.gerarEtapasComIA(user);
//
//        // Cria um objeto Trilha temporário para mostrar no template
//        Trilha trilha = Trilha.builder()
//                .titulo("Trilha Personalizada (Gerada Dinamicamente)")
//                .descricao("Esta trilha foi criada dinamicamente pela IA sem salvar no banco")
//                .dataCriacao(LocalDate.now())
//                .user(user)
//                .etapas(
//                        etapasGeradas.stream().map(nome -> Etapa.builder()
//                                .nome(nome)
//                                .descricao("Etapa sugerida pela IA")
//                                .concluida(false)
//                                .build()
//                        ).toList()
//                )
//                .build();
//
//        model.addAttribute("trilha", trilha);
//
//        return "trilha-gerada"; // template novo
//    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {

        var trilha = trilhaService.getTrilhaById(id);

        model.addAttribute("trilha", trilhaService.getTrilhaById(id));
        model.addAttribute("etapas", trilha.getEtapas());

        return "trilha";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        trilhaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.delete.success"));
        return "redirect:/trilha";
    }


}
