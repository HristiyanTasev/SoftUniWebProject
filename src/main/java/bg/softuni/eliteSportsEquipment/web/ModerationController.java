package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserDTO;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ModerationController {

    private final UserService userService;

    public ModerationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/service/users")
    public String servicePage(Model model) {

        List<UserDTO> users = this.userService.getAllUsers();

        model.addAttribute("users", users);

        return "users";
    }

    //TODO make admin panel so admins can assign roles to all users
}
