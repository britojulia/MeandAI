package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.trilha.TrilhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public String index(Model model) {
        var skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "index";
    }
}
