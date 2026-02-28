package bg.softuni.eliteSportsEquipment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressDTO {

    @Size(min = 2, max = 30)
    @NotBlank
    private String city;

    @Size(min = 2, max = 30)
    @NotBlank
    private String address;

    public AddressDTO() {
    }

    public String getCity() {
        return city;
    }

    public AddressDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AddressDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}

