package bg.softuni.eliteSportsEquipment.model.dto.order;

import javax.validation.constraints.NotBlank;

public class CartUpdateProductDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String size;

    private Integer productQuantity;
}
