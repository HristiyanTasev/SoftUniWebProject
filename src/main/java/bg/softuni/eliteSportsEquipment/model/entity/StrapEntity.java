package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.TypeOfProductEnum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Locale;

@Entity
@DiscriminatorValue(value = "strap")
public class StrapEntity extends ProductEntity {

    private static final String PRODUCT_TYPE = TypeOfProductEnum.STRAP.toString().toLowerCase(Locale.ROOT);

    @Enumerated(EnumType.STRING)
    private StrapTypeEnum strapType;

    public StrapEntity() {
    }

    public StrapEntity(String brand, String name, String description, BigDecimal price) {
        super(PRODUCT_TYPE, brand, name, description, price);
    }

    public StrapTypeEnum getStrapType() {
        return strapType;
    }

    public StrapEntity setStrapType(StrapTypeEnum strapType) {
        this.strapType = strapType;
        return this;
    }
}
