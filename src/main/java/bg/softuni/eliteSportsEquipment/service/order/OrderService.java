package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.OrderMapper;
import bg.softuni.eliteSportsEquipment.repository.CartRepository;
import bg.softuni.eliteSportsEquipment.repository.OrderProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.OrderRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
//            createOrderAndClearCart("admin@mail.com");
            initOrder("PENDING");
            initOrder("TRAVELING");
            initOrder("COMPLETED");
        }
    }

    public void initOrder(String orderStatus) {
        UserEntity currentUser = this.userRepository.findById(1L).orElseThrow();
        CartEntity cart = this.cartRepository.findByIdEager(1L).orElseThrow();

        OrderEntity userOrder = new OrderEntity();

        userOrder.setOrderStatus(OrderStatusEnum.valueOf(orderStatus))
                .setCreatedAt(LocalDateTime.now())
                .setOrderProducts(cart
                        .getCartProducts()
                        .stream()
                        .map(oP -> {
                            OrderProductEntity orderProductEntity = this.orderMapper.cartProductEntityToOrderProductEntity(oP);
                            this.orderProductsRepository.save(orderProductEntity);
                            return orderProductEntity;
                        })
                        .collect(Collectors.toList())).setUser(currentUser)
                .setFinalPrice(cart.getCartProducts()
                        .stream()
                        .map(oP -> oP.getProduct().getPrice().multiply(BigDecimal.valueOf(oP.getProductQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        this.orderRepository.save(userOrder);
        currentUser.getOrders().add(userOrder);
        this.userRepository.save(currentUser);
    }

    public void createOrderAndClearCart(String email) {
        UserEntity currentUser = this.userRepository.findByEmailEager(email).orElseThrow();
        CartEntity cart = this.cartRepository.findByUserEmailEager(email).orElseThrow();

        if (cart.getCartProducts().size() <= 0) {
            throw new IllegalArgumentException();
        }

        OrderEntity userOrder = new OrderEntity();

        userOrder
                .setOrderStatus(OrderStatusEnum.PENDING)
                .setCreatedAt(LocalDateTime.now())
                .setOrderProducts(cart
                        .getCartProducts()
                        .stream()
                        .map(oP -> {
                            OrderProductEntity orderProductEntity = this.orderMapper.cartProductEntityToOrderProductEntity(oP);
                            this.orderProductsRepository.save(orderProductEntity);
                            return orderProductEntity;
                        })
                        .collect(Collectors.toList()))
                .setUser(currentUser)
                .setFinalPrice(cart.getCartProducts()
                        .stream()
                        .map(oP -> oP.getProduct().getPrice().multiply(BigDecimal.valueOf(oP.getProductQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        this.orderRepository.save(userOrder);
        currentUser.getOrders().add(userOrder);
        currentUser.setCart(null);
        this.userRepository.save(currentUser);
        this.cartRepository.deleteById(cart.getId());
    }
}
