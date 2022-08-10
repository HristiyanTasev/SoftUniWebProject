package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Query("SELECT c FROM CartEntity c JOIN FETCH c.cartProducts WHERE c.user.email = :email")
    Optional<CartEntity> findByUserEmailEager(String email);

    @Query("SELECT c FROM CartEntity c JOIN FETCH c.cartProducts WHERE c.id = :id")
    Optional<CartEntity> findByIdEager(Long id);

    Optional<CartEntity> findByUserEmail(String email);
}
