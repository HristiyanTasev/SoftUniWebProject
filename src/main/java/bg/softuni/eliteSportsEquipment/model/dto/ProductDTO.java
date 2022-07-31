package bg.softuni.eliteSportsEquipment.model.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String productType;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, BigDecimal price, String productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public ProductDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public ProductDTO setProductType(String productType) {
        this.productType = productType;
        return this;
    }
}
