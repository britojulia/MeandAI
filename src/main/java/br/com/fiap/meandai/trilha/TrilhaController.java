package br.com.fiap.meandai.trilha;

import jakarta.validation.Valid;
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

    private final TrilhaService trilhaService;

    @GetMapping
    public String index(Model model) {
        var trilhas = trilhaService.getAllTrilhas();
        model.addAttribute("trilhas", trilhas);
        return "index";
    }

    @GetMapping("/form/{userId}")
    public String form(@PathVariable Long userId, Model model) {
        model.addAttribute("trilha", new Trilha());
        model.addAttribute("userId", userId);
        return "forms/trilhaForm";
    }

    @PostMapping
    public String save( @Valid
            @RequestParam Long userId,
            Trilha trilha,
            @RequestParam List<String> etapasForm, RedirectAttributes redirect
    ) {
        var criada = trilhaService.createTrilha(trilha, etapasForm, userId);
        redirect.addFlashAttribute("message", "Novo evento cadastrado com sucesso!");
        return "redirect:/trilha/" + criada.getId();
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        model.addAttribute("trilhaId", id);
        return "trilha";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        trilhaService.deleteById(id);
        redirect.addFlashAttribute("message", "Trilha deletada com sucesso!");
        return "redirect:/trilha";
    }


}
