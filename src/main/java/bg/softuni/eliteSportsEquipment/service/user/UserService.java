package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.*;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.UserMapper;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRoleRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
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
                .setAddress("Grad", "Adres")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(admin);
    }

    public void initModerator(Set<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@mail.com")
                .setAddress("Grad", "Adres")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(moderator);
    }

    public void initUser() {
        UserEntity user = new UserEntity()
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@mail.com")
                .setAddress("Grad", "Adres")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(user);
    }

    public void login(UserEntity userEntity) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {

        UserEntity newUser = userMapper.userDtoToUserEntity(userRegisterDTO);
        newUser.setAddress(userRegisterDTO.getCity(), userRegisterDTO.getAddress());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        this.userRepository.save(newUser);

        this.login(newUser);
    }

    public void editAddress(AddressDTO addressDTO, String email) {
        UserEntity currentUser = getUserByEmail(email);

        currentUser.setAddress("", "");

        currentUser.setAddress(addressDTO.getCity(), addressDTO.getAddress());

        this.userRepository.save(currentUser);
    }

    public UserProfileDTO getUserDetails(String email) {
        UserEntity currentUser = getUserByEmail(email);

        List<UserOrdersDTO> userOrders = new ArrayList<>();

        if (currentUser.getOrders() != null && currentUser.getOrders().size() >= 1) {
            userOrders = currentUser
                    .getOrders()
                    .stream()
                    .map(orderEntity ->
                            new UserOrdersDTO(orderEntity.getId(),
                                    orderEntity.getCreatedAt(),
                                    orderEntity.getOrderStatus().name()))
                    .collect(Collectors.toList());
        }

        List<String> roles = currentUser.getRoles().stream().map(r -> r.getUserRole().toString()).collect(Collectors.toList());

        return new UserProfileDTO(roles,
                currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getAddress(),
                userOrders);
    }

    public UserEntity getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow();
    }

    public List<UserDTO> getAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> {
                    UserDTO userDTO = new UserDTO();

                    userDTO.setId(u.getId())
                            .setEmail(u.getEmail())
                            .setUserRoles(u.getRoles().stream().map(userRole -> userRole.getUserRole().name()).collect(Collectors.toList()));

                    if (userDTO.getUserRoles().isEmpty()) {
                        userDTO.setUserRoles(List.of("NONE"));
                    }

                    List<String> allRoles = Arrays.stream(UserRoleEnum.values()).map(Enum::name).collect(Collectors.toList());

                    List<String> rolesToAdd = allRoles
                            .stream()
                            .filter(uR -> !userDTO.getUserRoles().contains(uR))
                            .collect(Collectors.toList());

                    userDTO.setRolesToAdd(rolesToAdd);

                    if (rolesToAdd.isEmpty()) {
                        userDTO.setRolesToAdd(List.of("NONE"));
                    }

                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    public AddressDTO getUserAddress(String email) {
        String[] split = this.getUserByEmail(email)
                .getAddress()
                .split(", ");

        AddressDTO address = new AddressDTO();
        address.setCity(split[0]).setAddress(split[1]);

        return address;
    }

    public boolean removeRoleFromUserById(UserRoleDTO userRoleDTO) {
        UserEntity user = this.userRepository.findById(userRoleDTO.getId()).orElseThrow();
        List<UserRoleEnum> dtoRoles = Arrays.stream(UserRoleEnum.values())
                .filter(r -> r.name()
                        .equals(userRoleDTO.getRole()))
                .collect(Collectors.toList());

        boolean removeSuccess = false;

        if (dtoRoles.size() == 1) {
            UserRoleEntity role = this.userRoleRepository.findByUserRole(dtoRoles.get(0)).orElseThrow();
            removeSuccess = user.getRoles().remove(role);
            this.userRepository.save(user);
        }

        return removeSuccess;
    }

    public boolean addRoleToUserById(UserRoleDTO userRoleDTO) {
        UserEntity user = this.userRepository.findById(userRoleDTO.getId()).orElseThrow();
        List<UserRoleEnum> dtoRoles = Arrays.stream(UserRoleEnum.values())
                .filter(r -> r.name()
                        .equals(userRoleDTO.getRole()))
                .collect(Collectors.toList());


        if (dtoRoles.size() == 1) {
            UserRoleEntity role = this.userRoleRepository.findByUserRole(dtoRoles.get(0)).orElseThrow();
            user.getRoles().add(role);
            this.userRepository.save(user);
            return true;
        }

        return false;
    }
}
