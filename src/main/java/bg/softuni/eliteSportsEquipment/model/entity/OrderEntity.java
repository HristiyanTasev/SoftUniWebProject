package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.OrderStatusEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @OneToMany
    private List<BeltEntity> belts = new ArrayList<>();

    @OneToMany
    private List<StrapEntity> straps = new ArrayList<>();

    @OneToMany
    private List<SleeveEntity> sleeves = new ArrayList<>();

    public OrderEntity() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<BeltEntity> getBelts() {
        return belts;
    }

    public OrderEntity setBelts(List<BeltEntity> belts) {
        this.belts = belts;
        return this;
    }

    public List<StrapEntity> getStraps() {
        return straps;
    }

    public OrderEntity setStraps(List<StrapEntity> straps) {
        this.straps = straps;
        return this;
    }

    public List<SleeveEntity> getSleeves() {
        return sleeves;
    }

    public OrderEntity setSleeves(List<SleeveEntity> sleeves) {
        this.sleeves = sleeves;
        return this;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public OrderEntity setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
}
