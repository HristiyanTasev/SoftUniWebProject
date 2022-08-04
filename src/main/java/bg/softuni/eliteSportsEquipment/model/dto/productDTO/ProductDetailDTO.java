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
    private String beltLeverType;
    private String beltMaterialType;
    private String strapType;
    private String sleeveType;
    private String description;

    public ProductDetailDTO() {
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

    public String getBeltLeverType() {
        return beltLeverType;
    }

    public ProductDetailDTO setBeltLeverType(String beltLeverType) {
        this.beltLeverType = beltLeverType;
        return this;
    }

    public String getBeltMaterialType() {
        return beltMaterialType;
    }

    public ProductDetailDTO setBeltMaterialType(String beltMaterialType) {
        this.beltMaterialType = beltMaterialType;
        return this;
    }

    public String getStrapType() {
        return strapType;
    }

    public ProductDetailDTO setStrapType(String strapType) {
        this.strapType = strapType;
        return this;
    }

    public String getSleeveType() {
        return sleeveType;
    }

    public ProductDetailDTO setSleeveType(String sleeveType) {
        this.sleeveType = sleeveType;
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
