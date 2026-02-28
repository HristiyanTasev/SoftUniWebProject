package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProducts;

    @OneToOne
    private UserEntity user;

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

    public UserEntity getUser() {
        return user;
    }

    public CartEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
