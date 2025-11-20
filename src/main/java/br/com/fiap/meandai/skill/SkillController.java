package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.trilha.TrilhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {

    private final MessageHelper messageHelper;
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

        model.addAttribute("skills", skillService.findByUserId(userId));
        model.addAttribute("skill", new Skill());
        model.addAttribute("userId", userId);
        model.addAttribute("count", totalSkills);

        return "forms/skillForm";
    }


    @PostMapping
    public String save(@Valid Long userId, Skill skill, RedirectAttributes redirect) {
        skillService.save(skill, userId);
        redirect.addFlashAttribute("message", "Novo evento cadastrado com sucesso!");

        int totalSkills = skillService.countSkillsByUser(userId);

        //validação de no minino 3 skills
        if (totalSkills < 3) {
            return "redirect:/skill/formSkill/" + userId;
        }
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/skill/formSkill/" + userId;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        skillService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.delete.success"));
        return "redirect:/trilha/formSkill/";
    }


}
