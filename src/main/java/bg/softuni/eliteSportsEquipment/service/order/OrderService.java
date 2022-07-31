package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.entity.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.OrderEntity;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;
import bg.softuni.eliteSportsEquipment.repository.CartRepository;
import bg.softuni.eliteSportsEquipment.repository.OrderRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void init() {
        if (this.orderRepository.count() < 1) {
            initOrder("PENDING");
            initOrder("TRAVELING");
            initOrder("COMPLETED");
        }
    }

    public void initOrder(String orderStatus) {
        UserEntity user = this.userRepository.findById(1L).orElseThrow();
        CartEntity userCart = this.cartRepository.findById(user.getCart().getId()).orElseThrow();

        OrderEntity userOrder = new OrderEntity();
        userOrder.setOrderStatus(OrderStatusEnum.valueOf(orderStatus))
                .setCreatedAt(LocalDateTime.now())
                .setProducts(userCart.getProducts());

        user.getOrders().add(userOrder);

        this.userRepository.save(user);
    }
}
