package bg.softuni.eliteSportsEquipment.config;

import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import bg.softuni.eliteSportsEquipment.service.user.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/contacts", "/products/all",
                                "/products/belts", "/products/straps", "/products/sleeves",
                                "/products/details/**", "/maintenance", "/products/search/**").permitAll()
                        .requestMatchers("/users/login", "/users/register", "/users/login-error").anonymous()
                        //TODO: implement orders management page for moderators
                        .requestMatchers("/service/orders").hasRole(UserRoleEnum.MODERATOR.name())
                        .requestMatchers("/service/users", "/products/add/belt", "/products/add/sleeve", "/products/add/strap")
                            .hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        .defaultSuccessUrl("/")
                        .failureUrl("/users/login-error")
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Primary
    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }
}
