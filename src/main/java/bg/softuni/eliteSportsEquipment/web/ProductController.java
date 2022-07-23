package bg.softuni.eliteSportsEquipment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products/add/belt")
    private String beltAdd() {

        return "add-belt";
    }

    @GetMapping("/products/add/strap")
    private String strapAdd() {

        return "add-strap";
    }

    @GetMapping("/products/add/sleeve")
    private String sleeveAdd() {

        return "add-sleeve";
    }
}
