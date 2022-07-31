package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.ProductDTO;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final AllProductsService allProductsService;

    public ProductController(AllProductsService allProductsService) {
        this.allProductsService = allProductsService;
    }

    @GetMapping("/all")
    private String allProducts(Model model) {
        List<ProductDTO> allProducts = this.allProductsService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "products";
    }

    @GetMapping("/details/{id}")
    private String productDetails(@PathVariable("id") Long productId) {
        this.allProductsService.getProductById(productId);

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
