package bg.softuni.eliteSportsEquipment.util;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Service
public class TestUserDataService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = new UserEntity();
        user.setEmail(username)
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("User")
                .setEnabled(true);

        return new AppUserDetails(
                user,
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_MODERATOR"))
        );
    }
}
