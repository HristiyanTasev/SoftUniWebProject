package bg.softuni.eliteSportsEquipment.service;

import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void init() {
        if (this.userRepository.count() == 0 && this.userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            adminRole = this.userRoleRepository.save(adminRole);
            moderatorRole = this.userRoleRepository.save(moderatorRole);

            initAdmin(Set.of(adminRole, moderatorRole));
            initModerator(Set.of(moderatorRole));
            initUser();
        }
    }

    public void initAdmin(Set<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity()
                .setRoles(roles)
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setEmail("admin@mail.com")
                .setUsername("admin123")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(admin);
    }
    public void initModerator(Set<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@mail.com")
                .setUsername("moderator123")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(moderator);
    }

    public void initUser() {
        UserEntity user = new UserEntity()
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@mail.com")
                .setUsername("user123")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(user);
    }
}
