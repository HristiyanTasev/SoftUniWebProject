package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import java.util.List;

public class UserDetailsDTO {

    private String fullName;
    private String email;
    private String address;
    private List<UserOrdersDTO> orders;

    public UserDetailsDTO(String fullName, String email, String address, List<UserOrdersDTO> orders) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.orders = orders;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDetailsDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserDetailsDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<UserOrdersDTO> getOrders() {
        return orders;
    }

    public UserDetailsDTO setOrders(List<UserOrdersDTO> orders) {
        this.orders = orders;
        return this;
    }
}
