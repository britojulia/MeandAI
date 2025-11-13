package br.com.fiap.meandai.trilha;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
