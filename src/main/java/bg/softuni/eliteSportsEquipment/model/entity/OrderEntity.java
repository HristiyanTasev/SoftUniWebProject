package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    private LocalDateTime createdAt;

    private boolean isCompleted;

    private boolean isTravelling;

    @OneToMany
    private List<BeltEntity> belts = new ArrayList<>();

    @OneToMany
    private List<StrapEntity> straps = new ArrayList<>();

    @OneToMany
    private List<SleeveEntity> sleeves = new ArrayList<>();

    public OrderEntity() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<BeltEntity> getBelts() {
        return belts;
    }

    public OrderEntity setBelts(List<BeltEntity> belts) {
        this.belts = belts;
        return this;
    }

    public List<StrapEntity> getStraps() {
        return straps;
    }

    public OrderEntity setStraps(List<StrapEntity> straps) {
        this.straps = straps;
        return this;
    }

    public List<SleeveEntity> getSleeves() {
        return sleeves;
    }

    public OrderEntity setSleeves(List<SleeveEntity> sleeves) {
        this.sleeves = sleeves;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public OrderEntity setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    public boolean isTravelling() {
        return isTravelling;
    }

    public OrderEntity setTravelling(boolean travelling) {
        isTravelling = travelling;
        return this;
    }
}
