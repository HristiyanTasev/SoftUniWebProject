package bg.softuni.eliteSportsEquipment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModerationController {

    @GetMapping("/service/orders")
    public String servicePage() {

        return "service-page";
    }

    //TODO make admin panel so admins can assign roles to all users
}
