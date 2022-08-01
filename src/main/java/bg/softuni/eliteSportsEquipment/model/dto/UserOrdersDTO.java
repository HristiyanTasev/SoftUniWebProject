package bg.softuni.eliteSportsEquipment.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class UserOrdersDTO {

    private Long orderId;

    //TODO Show createdAt properly
    private LocalDateTime createdAt;
    private String orderStatus;

    public UserOrdersDTO(Long orderId, LocalDateTime createdAt, String orderStatus) {
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public UserOrdersDTO setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserOrdersDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public UserOrdersDTO setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
}
