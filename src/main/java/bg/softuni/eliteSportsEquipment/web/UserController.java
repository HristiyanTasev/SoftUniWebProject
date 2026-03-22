package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.EmailForPasswordResetDto;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.PasswordResetDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserProfileDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserFavouritesDTO;
import bg.softuni.eliteSportsEquipment.service.user.FavouriteService;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes
                .addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        if (!model.containsAttribute("emailForPasswordResetDto")) {
            model.addAttribute("emailForPasswordResetDto", new EmailForPasswordResetDto());
        }
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute("emailForPasswordResetDto") EmailForPasswordResetDto emailForPasswordResetDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailForPasswordResetDto", emailForPasswordResetDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.emailForPasswordResetDto",
                    bindingResult);

            return "redirect:/users/forgot-password";
        }

        userService.sendPasswordResetLink(emailForPasswordResetDto);
        redirectAttributes.addFlashAttribute("message",
                "Password reset link has been sent to " + emailForPasswordResetDto.getEmail() + " if an account with it exists.");

        return "redirect:/users/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        if (!model.containsAttribute("passwordResetDTO")) {
            model.addAttribute("passwordResetDTO", new PasswordResetDTO());
        }

        model.addAttribute("token", token);

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(name = "token") String token,
                                @Valid @ModelAttribute("passwordResetDTO") PasswordResetDTO passwordResetDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordResetDTO", passwordResetDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordResetDTO",
                    bindingResult);

            return "redirect:/users/reset-password?token=" + token;
        }
        userService.resetPassword(token, passwordResetDTO);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String userProfile(Principal principal, Model model) {
        UserProfileDTO userDetails = this.userService.getUserDetails(principal.getName());

        model.addAttribute("userDetails", userDetails);

        return "profile";
    }

    @GetMapping("/address")
    public String userAddress(Principal principal, Model model) {

        if (!model.containsAttribute("addressDTO")) {
            model.addAttribute("addressDTO", this.userService.getUserAddress(principal.getName()));
        }

        return "address";
    }

    @PostMapping("/address")
    public String userAddress(@Valid AddressDTO addressDTO,
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
    public String userFavourites(Model model, Principal principal) {
        String email = principal.getName();

        List<UserFavouritesDTO> favProducts = this.favouriteService.getFavProductsByEmail(email);

        model.addAttribute("favProducts", favProducts);

        return "favourites";
    }

    @PutMapping("/favourites/add/{id}")
    public String addToFavourite(@PathVariable(name = "id") Long productId, Principal principal) {

        this.favouriteService.addProductById(productId, principal.getName());

        return "redirect:/users/favourites";
    }

    @DeleteMapping("/favourites/delete/{id}")
    public String deleteFromFavourite(@PathVariable(name = "id") Long productId, Principal principal) {

        this.favouriteService.deleteProductById(productId, principal.getName());

        return "redirect:/users/favourites";
    }
}
