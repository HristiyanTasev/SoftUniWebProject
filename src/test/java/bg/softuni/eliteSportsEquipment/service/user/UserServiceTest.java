package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserProfileDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRoleDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.UserMapper;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    @Mock
    private UserRoleRepository mockRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserDetailsService mockUserDetailsService;

    @Mock
    private UserMapper mockUserMapper;

    private UserService toTest;

    @BeforeEach
    void setUp() {
        toTest = new UserService(
                mockUserRepo,
                mockRoleRepository,
                mockPasswordEncoder,
                mockUserDetailsService,
                mockUserMapper
        );
    }

    @Test
    void testGetUserAddress() {
        UserEntity testUser = new UserEntity()
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

        when(mockUserRepo.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        AddressDTO userAddress = toTest.getUserAddress(testUser.getEmail());

        Assertions.assertEquals("City", userAddress.getCity());
        Assertions.assertEquals("Address", userAddress.getAddress());
    }

    @Test
    void testGetUserDetails_UserExistsWithOrders() {
        UserEntity testUser = new UserEntity()
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

        LocalDateTime now = LocalDateTime.now();

        OrderEntity orderEntity = new OrderEntity()
                .setOrderProducts(List.of())
                .setOrderStatus(OrderStatusEnum.PENDING)
                .setUser(testUser)
                .setCreatedAt(now)
                .setFinalPrice(new BigDecimal(10));

        testUser.setOrders(List.of(orderEntity));

        when(mockUserRepo.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        UserProfileDTO userDetails = toTest.getUserDetails(testUser.getEmail());

        Assertions.assertEquals(testUser.getFullName(), userDetails.getFullName());
        Assertions.assertEquals(testUser.getEmail(), userDetails.getEmail());
        Assertions.assertEquals(testUser.getAddress(), userDetails.getAddress());
        Assertions.assertEquals(testUser.getOrders().size(), userDetails.getOrders().size());
    }

    @Test
    void testGetUserDetails_UserExistsWithoutOrders() {
        UserEntity testUser = new UserEntity()
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

        when(mockUserRepo.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        UserProfileDTO userDetails = toTest.getUserDetails(testUser.getEmail());

        Assertions.assertEquals(testUser.getFullName(), userDetails.getFullName());
        Assertions.assertEquals(testUser.getEmail(), userDetails.getEmail());
        Assertions.assertEquals(testUser.getAddress(), userDetails.getAddress());
        Assertions.assertEquals(0, userDetails.getOrders().size());
    }

    @Test
    void testGetUserDetails_UserDoesNotExist() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> toTest.getUserDetails("fake@email.com")
        );
    }

    @Test
    void testEditUserAddress_UserExists() {
        UserEntity testUser = new UserEntity()
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

        when(mockUserRepo.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        AddressDTO addressDTO = new AddressDTO().setCity("NewCity").setAddress("NewAddress");

        toTest.editAddress(addressDTO, testUser.getEmail());

        Assertions.assertEquals(addressDTO.getCity(), testUser.getAddress().substring(0, 7));
        Assertions.assertEquals(addressDTO.getAddress(), testUser.getAddress().substring(9, 19));
    }

    @Test
    void testRemoveRoleFromUserById_UserExists() {
        UserRoleEntity moderator = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);
        UserRoleEntity admin = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);

        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(moderator);
        roles.add(admin);

        UserEntity testUser = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAddress("City", "Address")
                .setRoles(roles);
        testUser.setId(1L);

        when(mockUserRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        when(mockRoleRepository.findByUserRole(UserRoleEnum.MODERATOR))
                .thenReturn(Optional.of(moderator));

        UserRoleDTO userRoleDTO = new UserRoleDTO().setRole("MODERATOR").setId(1L);

        Assertions.assertTrue(toTest.removeRoleFromUserById(userRoleDTO));
        Assertions.assertFalse(testUser.getRoles().contains(moderator));
    }

    @Test
    void testRemoveRoleFromUserById_UserDoesNotExist() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> toTest.removeRoleFromUserById(new UserRoleDTO().setRole("MODERATOR").setId(2L)));
    }
    @Test
    void testRemoveRoleFromUserById_UserRoleDoesNotExist() {
        UserRoleEntity moderator = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);
        UserRoleEntity admin = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);

        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(moderator);
        roles.add(admin);

        UserEntity testUser = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAddress("City", "Address")
                .setRoles(roles);
        testUser.setId(1L);

        when(mockUserRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        Assertions.assertFalse(toTest.removeRoleFromUserById(new UserRoleDTO().setRole("").setId(1L)));
    }

    @Test
    void testAddRoleToUserById_UserExists() {
        UserRoleEntity moderator = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);
        UserRoleEntity admin = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);

        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(moderator);

        UserEntity testUser = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAddress("City", "Address")
                .setRoles(roles);
        testUser.setId(1L);

        when(mockUserRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        when(mockRoleRepository.findByUserRole(UserRoleEnum.ADMIN))
                .thenReturn(Optional.of(admin));

        UserRoleDTO userRoleDTO = new UserRoleDTO().setRole("ADMIN").setId(1L);

        Assertions.assertTrue(toTest.addRoleToUserById(userRoleDTO));
        Assertions.assertTrue(testUser.getRoles().contains(admin));
    }

    @Test
    void testAddRoleToUserById_UserRoleDoesNotExist() {
        UserRoleEntity moderator = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);
        UserRoleEntity admin = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);

        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(moderator);

        UserEntity testUser = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("asdasd")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAddress("City", "Address")
                .setRoles(roles);
        testUser.setId(1L);

        when(mockUserRepo.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        UserRoleDTO userRoleDTO = new UserRoleDTO().setRole("fakeRole").setId(1L);

        Assertions.assertFalse(toTest.addRoleToUserById(userRoleDTO));
        Assertions.assertFalse(testUser.getRoles().contains(admin));
    }
}
