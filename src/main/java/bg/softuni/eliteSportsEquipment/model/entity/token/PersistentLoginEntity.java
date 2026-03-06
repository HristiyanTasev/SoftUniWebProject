package bg.softuni.eliteSportsEquipment.model.entity.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")
public class PersistentLoginEntity {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(nullable = false)
    private LocalDateTime lastUsed;

    @Column(nullable = false)
    private String username;

    public PersistentLoginEntity() {}

    public String getSeries() {
        return series;
    }

    public PersistentLoginEntity setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getToken() {
        return token;
    }

    public PersistentLoginEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public PersistentLoginEntity setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PersistentLoginEntity setUsername(String username) {
        this.username = username;
        return this;
    }
}
