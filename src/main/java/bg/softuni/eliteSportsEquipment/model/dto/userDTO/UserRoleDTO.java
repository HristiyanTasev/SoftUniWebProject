package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRoleDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String role;

    public UserRoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserRoleDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserRoleDTO setRole(String role) {
        this.role = role;
        return this;
    }
}
