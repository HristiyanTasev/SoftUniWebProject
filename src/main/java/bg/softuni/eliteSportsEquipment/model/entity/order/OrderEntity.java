package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @ManyToMany
    private List<OrderProductEntity> orderProducts = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal finalPrice;

    public OrderEntity() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public OrderEntity setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public List<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public OrderEntity setOrderProducts(List<OrderProductEntity> products) {
        this.orderProducts = products;
        return this;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public OrderEntity setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }
}
