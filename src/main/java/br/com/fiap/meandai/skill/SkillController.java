package br.com.fiap.meandai.skill;

import br.com.fiap.meandai.config.MessageHelper;
import br.com.fiap.meandai.trilha.TrilhaService;
import br.com.fiap.meandai.user.User;
import br.com.fiap.meandai.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
    private final UserService userService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        var skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "index";
    }

    @GetMapping("/formSkill")
    public String formSkill(Model model,
                            @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        int totalSkills = skillService.countSkillsByUser(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("skills", skillService.findByUserId(user.getId()));
        model.addAttribute("skill", new Skill());
        model.addAttribute("count", totalSkills);

        return "forms/skillForm";
    }


    @PostMapping
    public String save(@Valid Skill skill,
                       RedirectAttributes redirect,
                       @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        skillService.save(skill, user.getId());

        redirect.addFlashAttribute("message", messageHelper.get("message.success"));

        int totalSkills = skillService.countSkillsByUser(user.getId());

        //validação de no minino 3 skills
        if (totalSkills < 3) {
            return "redirect:/skill/formSkill";
        }
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/skill/formSkill";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect ){
        Skill skill = skillService.getSkillById(id);
        Long userId = skill.getUser().getId();
        skillService.deleteById(id);
        redirect.addFlashAttribute("message", messageHelper.get("message.success"));
        return "redirect:/skill/formSkill";
    }


}
