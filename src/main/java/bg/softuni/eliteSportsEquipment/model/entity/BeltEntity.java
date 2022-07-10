package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "belts")
public class BeltEntity extends BaseProductEntity {

    @Enumerated(EnumType.STRING)
    private BeltMaterialEnum materialType;

    @Enumerated(EnumType.STRING)
    private BeltLeverEnum leverType;

    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    public BeltEntity() {
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
