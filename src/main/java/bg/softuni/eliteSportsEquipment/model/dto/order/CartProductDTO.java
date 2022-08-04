package bg.softuni.eliteSportsEquipment.model.dto.order;

import java.math.BigDecimal;

public class CartProductDTO {

    private String name;
    private BigDecimal price;
    private Integer productQuantity;
    private BigDecimal totalPrice;

    public CartProductDTO() {
    }

    public String getName() {
        return name;
    }

    public CartProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CartProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public CartProductDTO setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public CartProductDTO setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
