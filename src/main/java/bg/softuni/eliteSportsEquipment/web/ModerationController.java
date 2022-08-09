package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRoleDTO;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ModerationController {

    private final UserService userService;

    public ModerationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRoleDTO")
    public UserRoleDTO initUserRoleDTO() {
        return new UserRoleDTO();
    }

    @GetMapping("/users")
    public String servicePage(Model model, Principal principal) {

        List<UserDTO> users = this.userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("currentUser", principal.getName());

        return "users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/role/remove")
    public String removeRole(@Valid UserRoleDTO userRoleDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.userService.removeRoleFromUserById(userRoleDTO)) {
            redirectAttributes.addFlashAttribute("userRoleDTO", userRoleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRoleDTO", bindingResult);

            return "redirect:/service/users";
        }

        return "redirect:/service/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/role/add")
    public String addRole(@Valid UserRoleDTO userRoleDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.userService.addRoleToUserById(userRoleDTO)) {
            redirectAttributes.addFlashAttribute("userRoleDTO", userRoleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRoleDTO", bindingResult);

            return "redirect:/service/users";
        }

        return "redirect:/service/users";
    }
}
