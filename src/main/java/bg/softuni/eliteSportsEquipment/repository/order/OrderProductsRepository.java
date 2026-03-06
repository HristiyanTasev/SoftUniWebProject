package bg.softuni.eliteSportsEquipment.repository.order;

import bg.softuni.eliteSportsEquipment.model.entity.order.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProductEntity, Long> {
}
