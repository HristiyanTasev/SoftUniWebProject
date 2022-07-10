package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "carts_belts",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "belt_id", referencedColumnName = "id"))
    private List<BeltEntity> belts;

    @ManyToMany
    @JoinTable(name = "carts_straps",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "strap_id", referencedColumnName = "id"))
    private List<StrapEntity> straps;

    @ManyToMany
    @JoinTable(name = "carts_sleeves",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sleeve_id", referencedColumnName = "id"))
    private List<SleeveEntity> sleeves;

    @OneToOne
    private UserEntity buyer;

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

    public UserEntity getBuyer() {
        return buyer;
    }

    public CartEntity setBuyer(UserEntity buyer) {
        this.buyer = buyer;
        return this;
    }
}
