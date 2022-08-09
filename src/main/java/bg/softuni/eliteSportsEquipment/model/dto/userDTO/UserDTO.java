package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import java.util.List;

public class UserDTO {

    private Long id;
    private String email;
    private List<String> userRoles;
    private List<String> rolesToAdd;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public UserDTO setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public List<String> getRolesToAdd() {
        return rolesToAdd;
    }

    public UserDTO setRolesToAdd(List<String> rolesToAdd) {
        this.rolesToAdd = rolesToAdd;
        return this;
    }
}
