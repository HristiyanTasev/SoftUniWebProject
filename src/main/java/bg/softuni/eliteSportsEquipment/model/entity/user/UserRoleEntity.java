package bg.softuni.eliteSportsEquipment.model.entity.user;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public UserRoleEntity() {
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRoleEntity setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public UserRoleEntity setUsers(Set<UserEntity> users) {
        this.users = users;
        return this;
    }
}
