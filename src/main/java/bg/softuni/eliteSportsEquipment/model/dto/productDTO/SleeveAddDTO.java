package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import bg.softuni.eliteSportsEquipment.model.validation.FileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SleeveAddDTO {
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
    private String sleeveType;

    @FileNotEmpty(message = "Picture is required!")
    private MultipartFile picture;

    public SleeveAddDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public SleeveAddDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getName() {
        return name;
    }

    public SleeveAddDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SleeveAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SleeveAddDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getSleeveType() {
        return sleeveType;
    }

    public SleeveAddDTO setSleeveType(String sleeveType) {
        this.sleeveType = sleeveType;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public SleeveAddDTO setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
