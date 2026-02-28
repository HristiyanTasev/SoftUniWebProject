package bg.softuni.eliteSportsEquipment.model.entity.product;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class ProductEntity extends BaseEntity {

    @Basic
    @Column(insertable = false, updatable = false)
    private String type;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne
    private PictureEntity picture;

    public ProductEntity() {
    }

    public ProductEntity(String type, String brand, String name, String description, BigDecimal price) {
        this.type = type;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public ProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getType() {
        return type;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public ProductEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }
}
