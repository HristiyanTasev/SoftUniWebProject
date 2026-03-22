package bg.softuni.eliteSportsEquipment.model.enums;

import java.time.Duration;

public enum TokenUsageEnum {
    EMAIL_VERIFICATION("/users/verify-email", Duration.ofHours(1)),
    PASSWORD_RESET("/users/reset-password", Duration.ofMinutes(15));

    private final String path;
    private final Duration expiration;

    TokenUsageEnum(String path, Duration expiration) {
        this.path = path;
        this.expiration = expiration;
    }

    public String getPath() {
        return path;
    }

    public Duration getExpiration() {
        return expiration;
    }
}
