package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.service.order.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
@Validated
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String userCart(Model model, Principal principal) {
        String email = principal.getName();

        if (!model.containsAttribute("userCart")) {
            CartDTO userCart = this.cartService.getCartByUserEmail(email);
            model.addAttribute("userCart", userCart);
        }

        return "cart";
    }

    @PatchMapping("/cart/update")
    public String updateCart(CartDTO cartDTO,
                             Principal principal) {

        this.cartService.updateCart(cartDTO, principal.getName());

        return "redirect:/users/cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable(name = "id") Long productId,
                            @RequestParam(required = false) String size,
                            Principal principal) {

        this.cartService.addProductToCartById(productId, principal.getName(), size);

        return "redirect:/users/cart";
    }

    @DeleteMapping("/cart/delete/{id}")
    public String deleteFromCart(@PathVariable(name = "id") Long productId, Principal principal) {

        this.cartService.deleteProductFromCartById(productId, principal.getName());

        return "redirect:/users/cart";
    }
}
