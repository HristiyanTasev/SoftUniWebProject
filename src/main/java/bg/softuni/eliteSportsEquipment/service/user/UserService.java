package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.exception.ResourceNotFoundException;
import bg.softuni.eliteSportsEquipment.exception.TokenValidationException;
import bg.softuni.eliteSportsEquipment.model.dto.AddressDTO;
import bg.softuni.eliteSportsEquipment.model.dto.userDTO.*;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.TokenUsageEnum;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.UserMapper;
import bg.softuni.eliteSportsEquipment.repository.user.UserRepository;
import bg.softuni.eliteSportsEquipment.repository.user.UserRoleRepository;
import bg.softuni.eliteSportsEquipment.service.email.EmailService;
import bg.softuni.eliteSportsEquipment.service.token.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final JwtTokenService authTokenService;
    private final EmailService emailService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService,
                       UserMapper userMapper,
                       JwtTokenService authTokenService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
        this.authTokenService = authTokenService;
        this.emailService = emailService;
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
                .setPassword(this.passwordEncoder.encode("asdasd"))
                .setEnabled(true);

        this.userRepository.save(admin);
    }

    public void initModerator(Set<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@mail.com")
                .setAddress("Grad", "Adres")
                .setPassword(this.passwordEncoder.encode("asdasd"))
                .setEnabled(true);

        this.userRepository.save(moderator);
    }

    public void initUser() {
        UserEntity user = new UserEntity()
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@mail.com")
                .setAddress("Grad", "Adres")
                .setPassword(this.passwordEncoder.encode("asdasd"))
                .setEnabled(true);

        this.userRepository.save(user);
    }

    public void login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        securityContextRepository.saveContext(context, request, response);
    }

    public void registerAndSendEmail(UserRegisterDTO userRegisterDTO) {

        UserEntity newUser = userMapper.userDtoToUserEntity(userRegisterDTO);
        newUser.setAddress(userRegisterDTO.getCity(), userRegisterDTO.getAddress());
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        String token = this.authTokenService.generateToken(userRegisterDTO.getEmail(),
                TokenUsageEnum.EMAIL_VERIFICATION);

        this.userRepository.save(newUser);

        String url = this.buildTokenUrl(TokenUsageEnum.EMAIL_VERIFICATION, token);
        this.emailService.sendVerificationEmail(userRegisterDTO.getEmail(), url);
    }

    public void verifyUserEmailAndLogin(String token, HttpServletRequest request, HttpServletResponse response) {
        assertValidToken(token, TokenUsageEnum.EMAIL_VERIFICATION,
                TokenUsageEnum.EMAIL_VERIFICATION.getPath(), "message",
                "Verification link is invalid or expired. You can request a new one.");
        String userEmailFromToken = this.authTokenService.getUserEmailFromToken(token);

        UserEntity user = this.userRepository.findByEmailIgnoreCase(userEmailFromToken)
                .orElseThrow(() -> ResourceNotFoundException.forUser(userEmailFromToken));

        if (user.isEnabled()) {
            throw new IllegalArgumentException("User is already verified!");
        }

        user.setEnabled(true);
        this.userRepository.save(user);
        this.login(user, request, response);
    }

    public void resendEmailVerification(String email) {
        String token = this.authTokenService.generateToken(email,
                TokenUsageEnum.EMAIL_VERIFICATION);
        String url = this.buildTokenUrl(TokenUsageEnum.EMAIL_VERIFICATION, token);
        this.emailService.sendVerificationEmail(email, url);
    }

    public void sendPasswordResetLink(EmailForPasswordResetDto email) {
        if (this.userRepository.findByEmailIgnoreCase(email.getEmail()).isPresent()) {
            String token = this.authTokenService.generateToken(email.getEmail(), TokenUsageEnum.PASSWORD_RESET);
            String url = this.buildTokenUrl(TokenUsageEnum.PASSWORD_RESET, token);

            this.emailService.sendPasswordResetEmail(email.getEmail(), url);
        }
    }

    public void resetPassword(String token, PasswordResetDTO passwordResetDTO) {
        assertValidToken(token, TokenUsageEnum.PASSWORD_RESET,
                TokenUsageEnum.PASSWORD_RESET.getPath(), "message",
                "Reset link is invalid or expired. You can request a new one.");

        String userEmailFromToken = this.authTokenService.getUserEmailFromToken(token);
        UserEntity user = this.userRepository.findByEmailIgnoreCase(userEmailFromToken)
                .orElseThrow(() -> ResourceNotFoundException.forUser(userEmailFromToken));

        user.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));
        this.userRepository.save(user);
    }

    public String buildTokenUrl(TokenUsageEnum type, String token) {

        return appBaseUrl + type.getPath() + "?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
    }

    private void assertValidToken(String token, TokenUsageEnum expectedUsage,
                                  String redirectPath, String flashKey, String flashValue) {
        if (!authTokenService.isValidToken(token, expectedUsage)) {
            throw new TokenValidationException("Invalid or expired token!",
                    redirectPath, flashKey, flashValue);
        }
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

        if (currentUser.getOrders() != null && !currentUser.getOrders().isEmpty()) {
            userOrders = currentUser
                    .getOrders()
                    .stream()
                    .map(orderEntity ->
                            new UserOrdersDTO(orderEntity.getId(),
                                    orderEntity.getCreatedAt(),
                                    orderEntity.getOrderStatus().name()))
                    .collect(Collectors.toList());
        }

        List<String> roles = currentUser.getRoles().stream()
                .map(r -> r.getUserRole().toString()).collect(Collectors.toList());

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

                    List<String> allRoles = Arrays.stream(UserRoleEnum.values()).map(Enum::name).toList();

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
                .toList();

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
                .toList();


        if (dtoRoles.size() == 1) {
            UserRoleEntity role = this.userRoleRepository.findByUserRole(dtoRoles.get(0)).orElseThrow();
            user.getRoles().add(role);
            this.userRepository.save(user);
            return true;
        }

        return false;
    }
}
