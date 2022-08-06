package bg.softuni.eliteSportsEquipment.model.entity.order;

import bg.softuni.eliteSportsEquipment.model.entity.BaseEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProductEntity extends BaseEntity {

    @ManyToOne
    private ProductEntity product;

    @Column(nullable = false)
    private Integer productQuantity;

    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    public OrderProductEntity() {
    }

    public ProductEntity getProduct() {
        return product;
    }

    public OrderProductEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public OrderProductEntity setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public SizeEnum getSize() {
        return size;
    }

    public OrderProductEntity setSize(SizeEnum size) {
        this.size = size;
        return this;
    }
}
