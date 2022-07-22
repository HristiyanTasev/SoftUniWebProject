package bg.softuni.eliteSportsEquipment.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @Email(message = "User email should be valid.")
    private String email;

    @Size(min = 2, max = 20)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 20)
    @NotBlank
    private String lastName;

    @Size(min = 5)
    @NotBlank
    private String password;

    @Size(min = 5)
    @NotBlank
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
