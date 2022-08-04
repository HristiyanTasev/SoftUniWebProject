package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.service.order.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String userCart(Model model, Principal principal) {
        String email = principal.getName();

        CartDTO userCart = this.cartService.getCartByUserEmail(email);

        model.addAttribute("userCart", userCart);

        return "cart";
    }

    @PutMapping("/cart/add/{id}")
    public String addToCart(@PathVariable(name = "id") Long productId, Principal principal) {

        this.cartService.addProductToCartById(productId, principal.getName());

        return "redirect:/users/cart";
    }
}
