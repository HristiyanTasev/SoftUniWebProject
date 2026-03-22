package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import bg.softuni.eliteSportsEquipment.model.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match."
)
public class PasswordResetDTO {
    @Size(min = 5)
    @NotBlank
    private String password;

    @Size(min = 5)
    @NotBlank
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public PasswordResetDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public PasswordResetDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
