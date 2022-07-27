package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public class BaseProductEntity extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public BaseProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BaseProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BaseProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public BaseProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }
}
