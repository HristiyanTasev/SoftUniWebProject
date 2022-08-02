package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.service.order.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private String userCart(Model model, Principal principal) {
//        String email = principal.getName();
//
//        this.cartService.getCartByUserEmail(email);

        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    private String addToCart(@PathVariable(name = "id") Long productId, Principal principal) {

        this.cartService.addProductById(productId, principal.getName());

        return "redirect:/users/cart";
    }
}
