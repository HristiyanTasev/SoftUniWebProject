package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForPasswordResetDto {
    @Email(message = "User email should be valid")
    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailForPasswordResetDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
