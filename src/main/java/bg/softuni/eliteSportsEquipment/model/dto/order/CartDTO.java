package bg.softuni.eliteSportsEquipment.model.dto.order;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {

    private List<CartProductDTO> cartProducts;
    private BigDecimal subTotal = new BigDecimal(0);

    public CartDTO() {
    }

    public List<CartProductDTO> getCartProducts() {
        return cartProducts;
    }

    public CartDTO setCartProducts(List<CartProductDTO> cartProducts) {
        this.cartProducts = cartProducts;
        return this;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public CartDTO setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
        return this;
    }
}
