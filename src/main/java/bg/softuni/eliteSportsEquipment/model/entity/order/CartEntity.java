package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<ProductEntity> products = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProducts;

    public CartEntity() {
    }

    public CartEntity(List<CartProductEntity> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public List<CartProductEntity> getCartProducts() {
        return cartProducts;
    }

    public CartEntity setCartProducts(List<CartProductEntity> products) {
        this.cartProducts = products;
        return this;
    }
}
