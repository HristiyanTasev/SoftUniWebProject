package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = { "orders" })
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    @EntityGraph(attributePaths = { "orders" })
    Optional<UserEntity> findByEmailEager(String email);
}
