package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.OrderMapper;
import bg.softuni.eliteSportsEquipment.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderProductsRepository orderProductsRepository,
                        UserRepository userRepository, CartRepository cartRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
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
        CartEntity byId = this.cartRepository.findById(1L).orElseThrow();

        OrderEntity userOrder = new OrderEntity();

        userOrder.setOrderStatus(OrderStatusEnum.valueOf(orderStatus))
                .setCreatedAt(LocalDateTime.now())
                .setOrderProducts(byId
                        .getCartProducts()
                        .stream()
                        .map(oP -> {
                            OrderProductEntity orderProductEntity = this.orderMapper.cartProductEntityToOrderProductEntity(oP);
                            this.orderProductsRepository.save(orderProductEntity);
                            return orderProductEntity;
                        })
                        .collect(Collectors.toList()));

        userOrder.setUser(user);
        userOrder.setFinalPrice(new BigDecimal(500));

        this.orderRepository.save(userOrder);
        user.getOrders().add(userOrder);
        this.userRepository.save(user);
    }
}
