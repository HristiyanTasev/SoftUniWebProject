package bg.softuni.eliteSportsEquipment.model.dto;

import java.math.BigDecimal;

public class ProductDetailDTO {
    private String name;
    private BigDecimal price;
    private String size;
    private String description;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(String name, BigDecimal price, String size, String description) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public ProductDetailDTO setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDetailDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSize() {
        return size;
    }

    public ProductDetailDTO setSize(String size) {
        this.size = size;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
