package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegisterDTO")
    private UserRegisterDTO initUserRegisterDTO() {
        return new UserRegisterDTO();
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
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO",
                            bindingResult);

            return "redirect:/users/register";
        }

        userService.registerAndLogin(userRegisterDTO);

        return "redirect:/";
    }
}
