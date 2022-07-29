package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.TypeOfProductEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Locale;

@Entity
@DiscriminatorValue(value = "belt")
public class BeltEntity extends ProductEntity {

    private static final String PRODUCT_TYPE = TypeOfProductEnum.BELT.toString().toLowerCase(Locale.ROOT);

    @Enumerated(EnumType.STRING)
    private BeltMaterialEnum materialType;

    @Enumerated(EnumType.STRING)
    private BeltLeverEnum leverType;

    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    public BeltEntity() {
    }

    public BeltEntity(String brand, String name, String description, BigDecimal price) {
        super(PRODUCT_TYPE, brand, name, description, price);
    }

    public BeltMaterialEnum getMaterialType() {
        return materialType;
    }

    public BeltEntity setMaterialType(BeltMaterialEnum materialType) {
        this.materialType = materialType;
        return this;
    }

    public BeltLeverEnum getLeverType() {
        return leverType;
    }

    public BeltEntity setLeverType(BeltLeverEnum leverType) {
        this.leverType = leverType;
        return this;
    }


    public SizeEnum getSize() {
        return size;
    }

    public BeltEntity setSize(SizeEnum size) {
        this.size = size;
        return this;
    }
}
