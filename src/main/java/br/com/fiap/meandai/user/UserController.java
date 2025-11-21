package br.com.fiap.meandai.user;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/form")
    public String form(Model model,
                       @AuthenticationPrincipal OAuth2User principal) {
        User user = userService.register(principal);
        model.addAttribute("user", user);
        return "forms/userForm";
    }

    @PostMapping
    public String save(@AuthenticationPrincipal OAuth2User principal,
                       @Valid User user) {
        User savedUser = userService.register(principal);

        savedUser.setAreaAtual(user.getAreaAtual());
        savedUser.setObjetivo(user.getObjetivo());

        userService.save(savedUser);
        return "redirect:/skill/formSkill" ;
    }


}
