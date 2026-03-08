package bg.softuni.eliteSportsEquipment.service.token;

import bg.softuni.eliteSportsEquipment.model.enums.TokenUsageEnum;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(String email, TokenUsageEnum tokenType) {
        try {
            Instant now = Instant.now();
            JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                    .subject(email)
                    .issuedAt(now)
                    .expiresAt(now.plus(tokenType.getExpiration()))
                    .claim("action", tokenType.toString())
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token.");
        }
    }

    public String getUserEmailFromToken(String token) {
        Jwt decodedToken = this.jwtDecoder.decode(token);

        return decodedToken.getSubject();
    }

    public String getTokenTypeFromToken(String token) {
        Jwt decodedToken = this.jwtDecoder.decode(token);

        return decodedToken.getClaimAsString("action");
    }
}
