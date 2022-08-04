package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
