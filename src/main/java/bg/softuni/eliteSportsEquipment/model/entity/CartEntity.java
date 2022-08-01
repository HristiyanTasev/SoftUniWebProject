package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProductEntity> products = new ArrayList<>();

    public CartEntity() {
    }

    public CartEntity(List<ProductEntity> products) {
        this.products = products;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public CartEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }
}
