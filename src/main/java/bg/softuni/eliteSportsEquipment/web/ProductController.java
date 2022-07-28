package bg.softuni.eliteSportsEquipment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/all")
    private String allProducts() {

        return "products";
    }

    @GetMapping("/details/1")
    private String productDetails() {

        return "product-details";
    }

    @GetMapping("/add/belt")
    private String beltAdd() {

        return "add-belt";
    }

    @GetMapping("/add/strap")
    private String strapAdd() {

        return "add-strap";
    }

    @GetMapping("/add/sleeve")
    private String sleeveAdd() {

        return "add-sleeve";
    }
}
