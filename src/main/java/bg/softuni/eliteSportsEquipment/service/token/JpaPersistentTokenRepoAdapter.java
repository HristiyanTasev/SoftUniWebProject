package bg.softuni.eliteSportsEquipment.service.token;

import bg.softuni.eliteSportsEquipment.model.entity.token.PersistentLoginEntity;
import bg.softuni.eliteSportsEquipment.repository.token.PersistentLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JpaPersistentTokenRepoAdapter implements PersistentTokenRepository {
    private final PersistentLoginRepository repo;

    public JpaPersistentTokenRepoAdapter(PersistentLoginRepository repo) {
        this.repo = repo;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLoginEntity entity = new PersistentLoginEntity();
        entity.setSeries(token.getSeries());
        entity.setToken(token.getTokenValue());
        entity.setLastUsed(toLocal(token.getDate()));
        entity.setUsername(token.getUsername());
        repo.save(entity);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLoginEntity entity = repo.findBySeries(series)
                .orElseThrow(() -> new IllegalStateException("Series not found"));
        entity.setToken(tokenValue);
        entity.setLastUsed(toLocal(lastUsed));
        repo.save(entity);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return repo.findBySeries(seriesId)
                .map(e -> new PersistentRememberMeToken(
                        e.getUsername(),
                        e.getSeries(),
                        e.getToken(),
                        toDate(e.getLastUsed())
                ))
                .orElse(null);
    }

    @Override
    @Transactional
    public void removeUserTokens(String username) {
        repo.deleteAllByUsername(username);
    }

    private LocalDateTime toLocal(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
