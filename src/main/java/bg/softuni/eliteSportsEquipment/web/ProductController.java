package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.BeltAddDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDetailDTO;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import bg.softuni.eliteSportsEquipment.service.product.BeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final AllProductsService allProductsService;
    private final BeltService beltService;

    @Autowired
    public ProductController(AllProductsService allProductsService, BeltService beltService) {
        this.allProductsService = allProductsService;
        this.beltService = beltService;
    }

    @ModelAttribute("beltAddDTO")
    public BeltAddDTO initBeltAddDTO() {
        return new BeltAddDTO();
    }

    @GetMapping("/all")
    private String allProducts(Model model) {
        List<ProductDTO> allProducts = this.allProductsService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "products";
    }

    @GetMapping("/details/{id}")
    private String productDetails(@PathVariable("id") Long productId, Model model) {
        ProductDetailDTO product = this.allProductsService.getProductById(productId);

        model.addAttribute("product", product);

        return "product-details";
    }

    @GetMapping("/add/belt")
    private String beltAdd(Model model) {
        List<String> materials = Arrays.stream(BeltMaterialEnum.values()).map(Enum::name).collect(Collectors.toList());
        List<String> levers = Arrays.stream(BeltLeverEnum.values()).map(Enum::name).collect(Collectors.toList());

        model.addAttribute("materials", materials);
        model.addAttribute("levers", levers);
        return "add-belt";
    }

    // TODO Service-ite stavat null sled kato dobavq preAuthorize anotaciqta (vsichko raboti predi tova)
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/belt")
    private String beltAdd(@Valid BeltAddDTO beltAddDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !this.beltService.addBelt(beltAddDTO)) {
            redirectAttributes.addFlashAttribute("beltAddDTO", beltAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beltAddDTO", bindingResult);

            //TODO add custom error handling for taken product name
//            redirectAttributes.addFlashAttribute("nameTaken", true);

            return "redirect:/products/add/belt";
        }


        return "redirect:/users/profile";
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
