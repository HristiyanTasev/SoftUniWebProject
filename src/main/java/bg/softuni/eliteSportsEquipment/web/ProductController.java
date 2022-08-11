package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.SearchDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.*;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import bg.softuni.eliteSportsEquipment.service.product.BeltService;
import bg.softuni.eliteSportsEquipment.service.product.SleeveService;
import bg.softuni.eliteSportsEquipment.service.product.StrapService;
import bg.softuni.eliteSportsEquipment.service.user.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
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
    private final FavouriteService favouriteService;

    @Autowired
    public ProductController(AllProductsService allProductsService, BeltService beltService,
                             StrapService strapService, SleeveService sleeveService, FavouriteService favouriteService) {
        this.allProductsService = allProductsService;
        this.beltService = beltService;
        this.strapService = strapService;
        this.sleeveService = sleeveService;
        this.favouriteService = favouriteService;
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
    public String allProducts(Model model,
                              @PageableDefault(page = 0, size = 12) Pageable pageable) {

        if (!model.containsAttribute("searchDTO")) {
            model.addAttribute("searchDTO", new SearchDTO());
        }
        Page<ProductDTO> allProducts = this.allProductsService.getAllProducts(pageable);

        model.addAttribute("beltBrands", this.allProductsService.findAllByType("belt"));
        model.addAttribute("strapBrands", this.allProductsService.findAllByType("strap"));
        model.addAttribute("sleeveBrands", this.allProductsService.findAllByType("sleeve"));

        model.addAttribute("products", allProducts);

        return "products";
    }

    @GetMapping("/search")
    public String search(@Valid SearchDTO searchDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("searchDTO", searchDTO);
            redirectAttributes.addAttribute(
                    "org.springframework.validation.BindingResult.searchDTO",
                    bindingResult);
            return "products-search";
        }

        if (!model.containsAttribute("searchDTO")) {
            model.addAttribute("searchDTO", searchDTO);
        }

        model.addAttribute("beltBrands", this.allProductsService.findAllByType("belt"));
        model.addAttribute("strapBrands", this.allProductsService.findAllByType("strap"));
        model.addAttribute("sleeveBrands", this.allProductsService.findAllByType("sleeve"));

        if (!searchDTO.isEmpty()) {
            model.addAttribute("products", this.allProductsService.searchProducts(searchDTO));
        }

        return "products-search";
    }

    @GetMapping("/details/{id}")
    public String productDetails(@PathVariable("id") Long productId, Model model, Principal principal) {
        ProductDetailDTO product = this.allProductsService.getProductById(productId);

        boolean inFavourites = false;
        if (principal != null) {
            inFavourites = this.favouriteService.productIsInFavourites(productId, principal.getName());
        }

        model.addAttribute("product", product);
        model.addAttribute("inFavourites", inFavourites);

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
