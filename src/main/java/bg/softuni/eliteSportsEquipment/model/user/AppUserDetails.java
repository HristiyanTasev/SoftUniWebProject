package bg.softuni.eliteSportsEquipment.model.user;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserDetails implements UserDetails {

    private final UserEntity user;
    private final Collection<GrantedAuthority> authorities;

    public AppUserDetails(UserEntity user,
                          Collection<GrantedAuthority> authorities) {

        this.user = user;
        this.authorities = authorities;
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
