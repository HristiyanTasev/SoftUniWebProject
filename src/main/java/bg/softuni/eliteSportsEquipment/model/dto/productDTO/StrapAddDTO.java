package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class StrapAddDTO {

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String strapType;

    public StrapAddDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public StrapAddDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public StrapAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StrapAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public StrapAddDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getStrapType() {
        return strapType;
    }

    public StrapAddDTO setStrapType(String strapType) {
        this.strapType = strapType;
        return this;
    }
}
