package bg.softuni.eliteSportsEquipment.model.entity.product;

import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.TypeOfProductEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Entity
@DiscriminatorValue(value = "sleeve")
public class SleeveEntity extends ProductEntity {

    private static final String PRODUCT_TYPE = TypeOfProductEnum.SLEEVE.toString().toLowerCase(Locale.ROOT);

    @Enumerated(EnumType.STRING)
    private SleeveTypeEnum sleeveType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<SizeEnum> sizes = List.of(SizeEnum.values());

    public SleeveEntity() {
    }

    public SleeveEntity(String brand, String name, String description, BigDecimal price) {
        super(PRODUCT_TYPE, brand, name, description, price);
    }

    public SleeveTypeEnum getSleeveType() {
        return sleeveType;
    }

    public SleeveEntity setSleeveType(SleeveTypeEnum sleeveType) {
        this.sleeveType = sleeveType;
        return this;
    }

    public List<SizeEnum> getSizes() {
        return sizes;
    }

    public SleeveEntity setSizes(List<SizeEnum> sizes) {
        this.sizes = sizes;
        return this;
    }
}
