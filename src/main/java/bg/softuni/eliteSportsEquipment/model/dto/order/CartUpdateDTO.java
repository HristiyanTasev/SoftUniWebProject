package bg.softuni.eliteSportsEquipment.model.dto.order;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class CartUpdateDTO {

    private List<CartUpdateProductDTO> products;

    public CartUpdateDTO() {
    }

    public List<CartUpdateProductDTO> getProducts() {
        return products;
    }

    public CartUpdateDTO setProducts(List<CartUpdateProductDTO> products) {
        this.products = products;
        return this;
    }
}
