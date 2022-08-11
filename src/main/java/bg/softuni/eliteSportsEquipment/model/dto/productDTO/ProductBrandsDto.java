package bg.softuni.eliteSportsEquipment.model.dto.productDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductBrandsDto {
    List<String> brands = new ArrayList<>();

    public ProductBrandsDto() {
    }

    public List<String> getBrands() {
        return brands;
    }

    public ProductBrandsDto setBrands(List<String> brands) {
        this.brands = brands;
        return this;
    }
}
