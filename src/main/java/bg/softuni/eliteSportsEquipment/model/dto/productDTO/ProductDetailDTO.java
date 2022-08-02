package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailDTO {

    private Long id;
    private String type;
    private String name;
    private BigDecimal price;
    private List<String> size = new ArrayList<>();
    private String description;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(Long id, String type, String name, BigDecimal price, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public ProductDetailDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public ProductDetailDTO setType(String type) {
        this.type = type;
        return this;
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

    public List<String> getSize() {
        return size;
    }

    public ProductDetailDTO setSize(List<String> size) {
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
