package bg.softuni.eliteSportsEquipment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RsaKeysConfiguration {

    // TODO (production): Replace this with persistent RSA keys loaded from a file, keystore, or secret manager.
    // Right now the keys are generated on startup for simplicity (dev/testing purposes only).
    @Bean
    public KeyPair generateRsaKeys() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No RSA algorithm found", e);
        }
    }
}
