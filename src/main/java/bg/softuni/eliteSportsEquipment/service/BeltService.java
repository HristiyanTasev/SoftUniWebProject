package bg.softuni.eliteSportsEquipment.service;

import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BeltService {

    private final AllProductsRepository allProductsRepository;

    public BeltService(AllProductsRepository allProductsRepository) {
        this.allProductsRepository = allProductsRepository;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("belt") < 1) {
            initOneBelt("SBD", "Kolan", "Long Belt Description",
                    40.99, "LEATHER", "PRONG", "M");

            initOneBelt("ESE", "Kolan2", "2Long Belt Description",
                    60.99, "LEATHER", "LEVER", "L");

            initOneBelt("SBD", "Kolan3", "Long Belt Description",
                    70.99, "LEATHER", "LEVER", "M");

            initOneBelt("SBD", "Kolan4", "Long Belt Description",
                    80.99, "NYLON", "PRONG", "S");
        }
    }

    private void initOneBelt(String brand, String name, String description,
                             Double price, String materialType,
                             String leverType, String size) {

        BeltEntity baseProduct = new BeltEntity(brand, name, description, BigDecimal.valueOf(price));

        baseProduct
                .setMaterialType(BeltMaterialEnum.valueOf(materialType))
                .setLeverType(BeltLeverEnum.valueOf(leverType))
                .setSize(SizeEnum.valueOf(size));

        this.allProductsRepository.save(baseProduct);
    }

    //@Enumerated(EnumType.STRING)
    //    private BeltMaterialEnum materialType;
    //
    //    @Enumerated(EnumType.STRING)
    //    private BeltLeverEnum leverType;
    //
    //    @Enumerated(EnumType.STRING)
    //    private SizeEnum size;
    //
    //    public BeltEntity() {
    //    }
    //
    //    public BeltEntity(String brand, String name, String description, BigDecimal price) {
    //        super(PRODUCT_TYPE, brand, name, description, price);
    //    }
}
