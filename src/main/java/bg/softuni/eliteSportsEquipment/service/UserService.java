package bg.softuni.eliteSportsEquipment.service;

import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.UserRoleEntity;
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

import java.util.Set;

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
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(admin);
    }

    public void initModerator(Set<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@mail.com")
                .setPassword(this.passwordEncoder.encode("asdasd"));

        this.userRepository.save(moderator);
    }

    public void initUser() {
        UserEntity user = new UserEntity()
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@mail.com")
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
        UserEntity currentUser = this.userRepository.findByEmail(email).get();

        currentUser.setAddress("", "");

//        String address = String.format("%s, %s", addressDTO.getCity(), addressDTO.getAddress());
        currentUser.setAddress(addressDTO.getCity(), addressDTO.getAddress());

        this.userRepository.save(currentUser);
    }
}
