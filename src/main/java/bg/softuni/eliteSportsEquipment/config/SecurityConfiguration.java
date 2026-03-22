package bg.softuni.eliteSportsEquipment.config;

import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.repository.user.UserRepository;
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

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           PersistentTokenRepository tokenRepo,
                                           UserDetailsService userDetailsService) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/contacts", "/products/all",
                                "/products/belts", "/products/straps", "/products/sleeves",
                                "/products/details/**", "/maintenance", "/products/search/**").permitAll()
                        .requestMatchers("/users/login", "/users/register", "/users/login-error",
                                "/users/verify-email", "/users/resend-verification", "/users/forgot-password",
                                "users/reset-password").anonymous()
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
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")
                        .rememberMeCookieName("elite-remember-me")
                        .tokenValiditySeconds(1209600) // 14 days
                        .tokenRepository(tokenRepo)
                        .userDetailsService(userDetailsService)
                        .useSecureCookie(true)
                        .key("eliteSportsEquipmentRememberMeKey")
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
