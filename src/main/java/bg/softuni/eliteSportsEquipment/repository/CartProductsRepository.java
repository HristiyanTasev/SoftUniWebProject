package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProductEntity, Long> {
}
