package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_products")
public class CartProductEntity extends BaseEntity {

    @ManyToOne
    private ProductEntity product;

    @Column(nullable = false)
    private Integer productQuantity;

    public CartProductEntity() {
    }

    public ProductEntity getProduct() {
        return product;
    }

    public CartProductEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public CartProductEntity setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }
}
