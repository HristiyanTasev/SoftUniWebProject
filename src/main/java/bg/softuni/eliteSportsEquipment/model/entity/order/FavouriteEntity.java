package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favourites")
public class FavouriteEntity extends BaseEntity {

    @ManyToMany
    private List<ProductEntity> products = new ArrayList<>();

    public FavouriteEntity() {
    }

    public FavouriteEntity(List<ProductEntity> products) {
        this.products = products;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public FavouriteEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }
}
