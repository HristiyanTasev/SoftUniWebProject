package bg.softuni.eliteSportsEquipment.repository.token;

import bg.softuni.eliteSportsEquipment.model.entity.token.PersistentLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLoginEntity, String> {
    Optional<PersistentLoginEntity> findBySeries(String series);
    void deleteAllByUsername(String username);
}
