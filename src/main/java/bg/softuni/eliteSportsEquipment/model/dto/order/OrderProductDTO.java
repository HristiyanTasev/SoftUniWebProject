package bg.softuni.eliteSportsEquipment.model.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderProductDTO {

    private Long id;
    private String name;
    private String size;
    private BigDecimal price;
    private Integer productQuantity;
    private BigDecimal totalPrice;
    private String pictureURL;

    public OrderProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public OrderProductDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getSize() {
        return size;
    }

    public OrderProductDTO setSize(String size) {
        this.size = size;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public OrderProductDTO setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderProductDTO setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public OrderProductDTO setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }
}
