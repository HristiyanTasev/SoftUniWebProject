package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
