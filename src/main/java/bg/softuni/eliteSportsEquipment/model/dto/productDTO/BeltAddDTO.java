package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class BeltAddDTO {

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
    private String materialType;

    @NotBlank
    private String leverType;

    @NotNull
    private MultipartFile picture;

    public BeltAddDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public BeltAddDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public BeltAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BeltAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BeltAddDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getMaterialType() {
        return materialType;
    }

    public BeltAddDTO setMaterialType(String materialType) {
        this.materialType = materialType;
        return this;
    }

    public String getLeverType() {
        return leverType;
    }

    public BeltAddDTO setLeverType(String leverType) {
        this.leverType = leverType;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public BeltAddDTO setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
