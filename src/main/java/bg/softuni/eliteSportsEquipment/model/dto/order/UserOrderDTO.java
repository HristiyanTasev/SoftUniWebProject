package bg.softuni.eliteSportsEquipment.model.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserOrderDTO {

    @NotNull
    private BigDecimal finalPrice;

    @NotNull
    private List<OrderProductDTO> orderProducts;

    @NotNull
    private LocalDateTime createdAt;

    @NotBlank
    private String orderStatus;

    private static final DateTimeFormatter CREATED_AT_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy ");

    public UserOrderDTO() {
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public UserOrderDTO setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public UserOrderDTO setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
        return this;
    }

    public String getCreatedAt() {
        return createdAt.format(CREATED_AT_FORMATTER);
    }

    public UserOrderDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public UserOrderDTO setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
}
