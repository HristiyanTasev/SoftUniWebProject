package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    private UserRegisterDTO initUserRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @GetMapping("/register")
    private String register() {
        return "register";
    }

    @PostMapping("/register")
    private String register(@Valid UserRegisterDTO userRegisterDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.userModel",
                    bindingResult);

            return "redirect:/register";
        }

        userService.registerAndLogin(userRegisterDTO);

        return "redirect:/";
    }

    @GetMapping("/profile")
    private String userProfile(Principal principal) {
        return "profile";
    }
}
