package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.trilha.TrilhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/formSkill/{userId}")
    public String formSkill(@PathVariable Long userId, Model model) {
        int totalSkills = skillService.countSkillsByUser(userId);

        model.addAttribute("skill", new Skill());
        model.addAttribute("userId", userId);
        model.addAttribute("count", totalSkills);

        return "forms/skillForm";
    }


    @PostMapping
    public String save(@Valid @RequestParam Long userId, Skill skill, Model model) {
        skillService.save(skill, userId);

        int totalSkills = skillService.countSkillsByUser(userId);

        if (totalSkills < 3) {
            // Ainda precisa adicionar mais skills
            return "redirect:/skill/formSkill/" + userId;
        }
        // Já tem 3 skills, vai para a próxima etapa da trilha
        return "redirect:/trilha/form/" + userId;
    }
}
