package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @OneToMany
    private List<BeltEntity> belts;

    @OneToMany
    private List<StrapEntity> straps;

    @OneToMany
    private List<SleeveEntity> sleeves;

    public CartEntity() {
    }

    public List<BeltEntity> getBelts() {
        return belts;
    }

    public CartEntity setBelts(List<BeltEntity> belts) {
        this.belts = belts;
        return this;
    }

    public List<StrapEntity> getStraps() {
        return straps;
    }

    public CartEntity setStraps(List<StrapEntity> straps) {
        this.straps = straps;
        return this;
    }

    public List<SleeveEntity> getSleeves() {
        return sleeves;
    }

    public CartEntity setSleeves(List<SleeveEntity> sleeves) {
        this.sleeves = sleeves;
        return this;
    }


}
