package bg.softuni.eliteSportsEquipment.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @OneToMany
    private List<ProductEntity> products;

    public CartEntity() {
    }


    public List<ProductEntity> getProducts() {
        return products;
    }

    public CartEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }
}
