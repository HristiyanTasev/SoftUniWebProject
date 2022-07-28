package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.UserDetailsDTO;
import bg.softuni.eliteSportsEquipment.model.dto.UserOrdersDTO;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("addressDTO")
    private AddressDTO initAddAddressDTO() {
        return new AddressDTO();
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes
                .addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    private String userProfile(Principal principal, Model model) {
        UserEntity currentUser = this.userService.getUserByEmail(principal.getName()).get();

        List<UserOrdersDTO> userOrders = currentUser
                .getOrders()
                .stream()
                .map(orderEntity ->
                        new UserOrdersDTO(orderEntity.getId(),
                                orderEntity.getCreatedAt(),
                                orderEntity.getOrderStatus().name()))
                .collect(Collectors.toList());

        UserDetailsDTO userDetails = new UserDetailsDTO(currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getAddress());

        model.addAttribute("userDetails", userDetails);

        return "profile";
    }

    @GetMapping("/address")
    private String userAddress(Principal principal, Model model) {

        return "address";
    }

    @PostMapping("/address")
    private String userAddress(@Valid AddressDTO addressDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addressDTO", addressDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.addressDTO",
                            bindingResult);

            return "redirect:/users/address";
        }

        String email = principal.getName();

        this.userService.editAddress(addressDTO, email);

        return "redirect:/users/profile";
    }

    @GetMapping("/cart")
    private String userCart() {

        return "cart";
    }

    @GetMapping("/favourites")
    private String userFavourites() {

        return "favourites";
    }
}
