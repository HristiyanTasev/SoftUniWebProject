package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import bg.softuni.eliteSportsEquipment.model.validation.FieldMatch;
import bg.softuni.eliteSportsEquipment.model.validation.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match."
)
public class UserRegisterDTO {

    @Email(message = "User email should be valid")
    @NotBlank(message = "User email should be provided")
    @UniqueUserEmail(message = "User email should be unique.")
    private String email;

    @Size(min = 2, max = 20)
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 20)
    @NotBlank
    private String lastName;

    @Size(min = 2, max = 30)
    @NotBlank
    private String city;

    @Size(min = 2, max = 30)
    @NotBlank
    private String address;

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

    public String getCity() {
        return city;
    }

    public UserRegisterDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserRegisterDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
