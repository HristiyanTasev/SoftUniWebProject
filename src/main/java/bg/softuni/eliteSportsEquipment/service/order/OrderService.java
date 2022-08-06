package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.OrderMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.CartRepository;
import bg.softuni.eliteSportsEquipment.repository.OrderRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AllProductsRepository allProductsRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository,
                        AllProductsRepository allProductsRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.allProductsRepository = allProductsRepository;
        this.orderMapper = orderMapper;
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
//        CartEntity userCart = user.getCart();
//
//        OrderEntity userOrder = new OrderEntity();
//
//        //TODO rework orders in db and fix the init
//        userOrder.setOrderStatus(OrderStatusEnum.valueOf(orderStatus))
//                .setCreatedAt(LocalDateTime.now())
//                .setOrderProducts(userCart
//                        .getCartProducts()
//                        .stream()
//                        .map(orderMapper::cartProductEntityToOrderProductEntity)
//                        .collect(Collectors.toList()));
//
//        user.getOrders().add(userOrder);

        this.userRepository.save(user);
    }
}
