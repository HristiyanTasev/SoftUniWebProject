package bg.softuni.eliteSportsEquipment.model.dto.order;

import jakarta.validation.constraints.NotBlank;

public class CartUpdateProductDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String size;

    private Integer productQuantity;
}
