package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER)
    private List<ProductEntity> products;

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
