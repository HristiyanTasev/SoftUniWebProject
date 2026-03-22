package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

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
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO",
                            bindingResult);

            return "redirect:/users/register";
        }

        userService.registerAndSendEmail(userRegisterDTO);

        redirectAttributes.addFlashAttribute("email", userRegisterDTO.getEmail());
        redirectAttributes.addFlashAttribute("message",
                "A verification email was sent to: " + userRegisterDTO.getEmail());

        return "redirect:/users/verify-email";
    }

    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "verify-email";
    }

    @GetMapping(value = "/verify-email", params = "token")
    public String confirmEmail(@RequestParam(name = "token") String token,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        userService.verifyUserEmailAndLogin(token, request, response);
        return "redirect:/";
    }

    @PostMapping("/resend-verification")
    public String resendVerification(@RequestParam String email, RedirectAttributes redirectAttributes) {
        this.userService.resendEmailVerification(email);

        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/users/verify-email";
    }
}
