package bg.softuni.eliteSportsEquipment.model.dto.order;

import java.math.BigDecimal;

public class CartProductDTO {

    private Long id;
    private String name;
    private String size;
    private BigDecimal price;
    private Integer productQuantity;
    private BigDecimal totalPrice;
    private String pictureURL;

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

    public String getSize() {
        return size;
    }

    public CartProductDTO setSize(String size) {
        this.size = size;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CartProductDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public CartProductDTO setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }
}
