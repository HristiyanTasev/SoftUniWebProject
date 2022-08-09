package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import java.util.List;

public class UserProfileDTO {

    private String fullName;
    private String email;
    private String address;
    private List<UserOrdersDTO> orders;

    public UserProfileDTO(String fullName, String email, String address, List<UserOrdersDTO> orders) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.orders = orders;
    }

    public String getFullName() {
        return fullName;
    }

    public UserProfileDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserProfileDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<UserOrdersDTO> getOrders() {
        return orders;
    }

    public UserProfileDTO setOrders(List<UserOrdersDTO> orders) {
        this.orders = orders;
        return this;
    }
}
