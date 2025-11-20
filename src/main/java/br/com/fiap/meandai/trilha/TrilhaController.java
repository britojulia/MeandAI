package br.com.fiap.meandai.trilha;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //gera a trilha sem salvar
    @GetMapping("/gerar/{userId}")
    public String gerarTrilhaDinamica(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        var resultado = trilhaService.gerarEtapasComIA(user);

        model.addAttribute("respostaIA", resultado.conteudo());
        model.addAttribute("user", user);

        return "trilha-gerada"; // mostra só o texto gerado pela IA
    }

    //salva a trilha com as etapas separadas
    @PostMapping("/{userId}")
    public String criarTrilha(@PathVariable Long userId, Model model, RedirectAttributes redirect) {
        Trilha trilha = trilhaService.createTrilha(userId); // retorna Trilha completa

        // adiciona a trilha no model para exibir na página
        model.addAttribute("trilha", trilha);

        redirect.addFlashAttribute("message", messageHelper.get("message.success"));

        // redireciona para a página de detalhes da trilha
        return "redirect:/trilha/" + trilha.getId();
    }

    @GetMapping("/{id}")
    public String detalhesTrilha(@PathVariable Long id, Model model) {
        Trilha trilha = trilhaService.getTrilhaById(id);
        model.addAttribute("trilha", trilha);
        return "trilha-detalhes";
    }

//    @GetMapping
//    public String listarTrilhas(Model model) {
//        List<Trilha> trilhas = trilhaService.getAllTrilhas();
//        model.addAttribute("trilhas", trilhas);
//        return "trilhas-lista";
//    }
//
//    @GetMapping("/{id}")
//    public String detalhesTrilha(@PathVariable Long id, Model model) {
//        Trilha trilha = trilhaService.getTrilhaById(id);
//        model.addAttribute("trilha", trilha);
//        return "trilha-detalhes"; //
//    }
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


    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        trilhaService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.delete.success"));
        return "redirect:/trilha";
    }


}
