package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.*;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.service.cloudinary.CloudinaryService;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import bg.softuni.eliteSportsEquipment.service.product.BeltService;
import bg.softuni.eliteSportsEquipment.service.product.SleeveService;
import bg.softuni.eliteSportsEquipment.service.product.StrapService;
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
    private final StrapService strapService;
    private final SleeveService sleeveService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(AllProductsService allProductsService, BeltService beltService,
                             StrapService strapService, SleeveService sleeveService,
                             CloudinaryService cloudinaryService) {
        this.allProductsService = allProductsService;
        this.beltService = beltService;
        this.strapService = strapService;
        this.sleeveService = sleeveService;
        this.cloudinaryService = cloudinaryService;
    }

    @ModelAttribute("beltAddDTO")
    public BeltAddDTO initBeltAddDTO() {
        return new BeltAddDTO();
    }

    @ModelAttribute("strapAddDTO")
    public StrapAddDTO initStrapAddDTO() {
        return new StrapAddDTO();
    }

    @ModelAttribute("sleeveAddDTO")
    public SleeveAddDTO initSleeveAddDTO() {
        return new SleeveAddDTO();
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        List<ProductDTO> allProducts = this.allProductsService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "products";
    }

    @GetMapping("/details/{id}")
    public String productDetails(@PathVariable("id") Long productId, Model model) {
        ProductDetailDTO product = this.allProductsService.getProductById(productId);

        model.addAttribute("product", product);

        return "product-details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add/belt")
    public String beltAdd(Model model) {
        List<String> materials = Arrays.stream(BeltMaterialEnum.values()).map(Enum::name).collect(Collectors.toList());
        List<String> levers = Arrays.stream(BeltLeverEnum.values()).map(Enum::name).collect(Collectors.toList());

        model.addAttribute("materials", materials);
        model.addAttribute("levers", levers);
        return "add-belt";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/belt")
    public String beltAdd(@Valid BeltAddDTO beltAddDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !this.beltService.addBelt(beltAddDTO)) {
            redirectAttributes.addFlashAttribute("beltAddDTO", beltAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beltAddDTO", bindingResult);

            //TODO add custom error handling for taken product name
//            redirectAttributes.addFlashAttribute("nameTaken", true);

            return "redirect:/products/add/belt";
        }


        return "redirect:/products/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add/strap")
    public String strapAdd(Model model) {
        List<String> strapTypes = Arrays.stream(StrapTypeEnum.values()).map(Enum::name).collect(Collectors.toList());

        model.addAttribute("strapTypes", strapTypes);

        return "add-strap";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/strap")
    public String strapAdd(@Valid StrapAddDTO strapAddDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !this.strapService.addStrap(strapAddDTO)) {
            redirectAttributes.addFlashAttribute("strapAddDTO", strapAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.strapAddDTO", bindingResult);

            return "redirect:/products/add/strap";
        }


        return "redirect:/products/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add/sleeve")
    public String sleeveAdd(Model model) {
        List<String> sleeveTypes = Arrays.stream(SleeveTypeEnum.values()).map(Enum::name).collect(Collectors.toList());

        model.addAttribute("sleeveTypes", sleeveTypes);
        return "add-sleeve";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/sleeve")
    public String sleeveAdd(@Valid SleeveAddDTO sleeveAddDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !this.sleeveService.addSleeve(sleeveAddDTO)) {
            redirectAttributes.addFlashAttribute("sleeveAddDTO", sleeveAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sleeveAddDTO", bindingResult);

            return "redirect:/products/add/sleeve";
        }


        return "redirect:/products/all";
    }
}
