package bg.softuni.eliteSportsEquipment.repository.order;

import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
