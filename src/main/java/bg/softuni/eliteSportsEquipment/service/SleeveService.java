package bg.softuni.eliteSportsEquipment.service;

import bg.softuni.eliteSportsEquipment.model.entity.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SleeveService {

    private final AllProductsRepository allProductsRepository;

    public SleeveService(AllProductsRepository allProductsRepository) {
        this.allProductsRepository = allProductsRepository;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("sleeve") < 1) {
            initOneSleeve("SBD", "Sleeve", "Long Sleeve Description",
                    40.99, "ELBOW", "M");

            initOneSleeve("ESE", "Sleeve2", "2Long Sleeve Description",
                    60.99, "KNEE", "L");

            initOneSleeve("SBD", "Sleeve3", "Long Sleeve Description",
                    70.99, "ELBOW", "M");

            initOneSleeve("SBD", "Sleeve4", "Long Sleeve Description",
                    80.99, "KNEE", "S");
        }
    }

    private void initOneSleeve(String brand, String name, String description,
                               Double price, String sleeveType, String size) {

        SleeveEntity baseProduct = new SleeveEntity(brand, name, description, BigDecimal.valueOf(price));

        baseProduct
                .setSleeveType(SleeveTypeEnum.valueOf(sleeveType))
                .setSize(SizeEnum.valueOf(size));

        this.allProductsRepository.save(baseProduct);
    }
}
