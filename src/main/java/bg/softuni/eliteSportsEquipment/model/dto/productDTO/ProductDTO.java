package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String productType;
    private String pictureURL;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, BigDecimal price, String productType, String pictureURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.pictureURL = pictureURL;
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

    public String getPictureURL() {
        return pictureURL;
    }

    public ProductDTO setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }
}
