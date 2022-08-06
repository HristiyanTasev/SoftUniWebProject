package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartUpdateDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.service.order.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartUpdateDTO")
    private CartUpdateDTO initCartUpdateDTO() {
        return new CartUpdateDTO();
    }

    @GetMapping("/cart")
    public String userCart(Model model, Principal principal) {
        String email = principal.getName();

        CartDTO userCart = this.cartService.getCartByUserEmail(email);

        model.addAttribute("userCart", userCart);

        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable(name = "id") Long productId,
                            @RequestParam(required = false) String size,
                            Principal principal) {

        this.cartService.addProductToCartById(productId, principal.getName(), size);

        return "redirect:/users/cart";
    }

    @PatchMapping("/cart/update")
    public String updateCart(@Valid CartUpdateDTO cartUpdateDTO,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/users/cart";
        }

        this.cartService.updateCart(cartUpdateDTO);

        return "redirect:/users/cart";
    }

    @DeleteMapping("/cart/delete/{id}")
    public String deleteFromCart(@PathVariable(name = "id") Long productId, Principal principal) {

        this.cartService.deleteProductFromCartById(productId, principal.getName());

        return "redirect:/users/cart";
    }
}
