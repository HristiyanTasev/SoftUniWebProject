package bg.softuni.eliteSportsEquipment.model.entity;

import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "straps")
public class StrapEntity extends BaseProductEntity {

    @Enumerated(EnumType.STRING)
    private StrapTypeEnum strapType;

    public StrapEntity() {
    }

    public StrapTypeEnum getStrapType() {
        return strapType;
    }

    public StrapEntity setStrapType(StrapTypeEnum strapType) {
        this.strapType = strapType;
        return this;
    }
}
