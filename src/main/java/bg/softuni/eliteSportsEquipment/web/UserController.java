package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserDetailsDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserFavouritesDTO;
import bg.softuni.eliteSportsEquipment.service.user.FavouriteService;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FavouriteService favouriteService;

    public UserController(UserService userService, FavouriteService favouriteService) {
        this.userService = userService;
        this.favouriteService = favouriteService;
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
        UserDetailsDTO userDetails = this.userService.getUserDetails(principal.getName());

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

    @GetMapping("/favourites")
    private String userFavourites(Model model, Principal principal) {
        String email = principal.getName();

        List<UserFavouritesDTO> favProducts = this.favouriteService.getFavProductsByEmail(email);

        model.addAttribute("favProducts", favProducts);

        return "favourites";
    }

    @GetMapping("/favourites/add/{id}")
    private String addToFavourite(@PathVariable(name = "id") Long productId, Principal principal) {

        this.favouriteService.addProductById(productId, principal.getName());

        return "redirect:/users/favourites";
    }

    @DeleteMapping("/favourites/delete/{id}")
    private String deleteFromFavourite(@PathVariable(name = "id") Long productId, Principal principal) {

        this.favouriteService.deleteProductById(productId, principal.getName());

        return "redirect:/users/favourites";
    }
}
