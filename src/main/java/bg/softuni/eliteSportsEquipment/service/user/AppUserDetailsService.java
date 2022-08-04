package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
    }

    private UserDetails map(UserEntity user) {
        return new AppUserDetails(
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user
                        .getRoles()
                        .stream()
                        .map(this::map)
                        .toList());
    }

    private GrantedAuthority map(UserRoleEntity role) {
        return new SimpleGrantedAuthority("ROLE_"
                + role.getUserRole().name());
    }
}
