package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sleeves")
public class SleeveEntity extends BaseProductEntity {

    @Enumerated(EnumType.STRING)
    private SleeveTypeEnum sleeveType;

    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    public SleeveEntity() {
    }

    public SleeveTypeEnum getSleeveType() {
        return sleeveType;
    }

    public SleeveEntity setSleeveType(SleeveTypeEnum sleeveType) {
        this.sleeveType = sleeveType;
        return this;
    }

    public SizeEnum getSize() {
        return size;
    }

    public SleeveEntity setSize(SizeEnum size) {
        this.size = size;
        return this;
    }
}
