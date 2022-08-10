package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    private AppUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(
                mockUserRepo
        );
    }

    @Test
    void testLoadByUsername_UserExists() {

        UserEntity testUserEntity = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAddress("City", "Address")
                .setRoles(
                        Set.of(
                                new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                                new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR)
                        )
                );

        when(mockUserRepo.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        AppUserDetails userDetails = (AppUserDetails) toTest.loadUserByUsername(testUserEntity.getEmail());

        Assertions.assertEquals(testUserEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUserEntity.getFullName(),
                userDetails.getFirstName() + " " + userDetails.getLastName());

        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        List<GrantedAuthority> authoritiesList = new ArrayList<>(authorities);

        //Might not pass sometimes because roles are in a HashSet
        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesList.get(0).getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.MODERATOR.name(),
                authoritiesList.get(1).getAuthority());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("fake@email.com")
        );
    }
}
