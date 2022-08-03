package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AllProductsService allProductsService;

    public HomeController(AllProductsService allProductsService) {
        this.allProductsService = allProductsService;
    }

    @GetMapping("/")
    private String home(Model model) {

        List<ProductDTO> featuredProducts = this.allProductsService.getFeaturedProducts();

        model.addAttribute("featuredProducts", featuredProducts);

        return "index";
    }
}
