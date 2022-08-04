package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.SleeveAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SleeveService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;

    public SleeveService(AllProductsRepository allProductsRepository, ProductMapper productMapper) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("sleeve") < 1) {
            initOneSleeve("SBD", "Sleeve", "Long Sleeve Description",
                    40.99, "ELBOW");

            initOneSleeve("ESE", "Sleeve2", "2Long Sleeve Description",
                    60.99, "KNEE");

            initOneSleeve("SBD", "Sleeve3", "Long Sleeve Description",
                    70.99, "ELBOW");

            initOneSleeve("SBD", "Sleeve4", "Long Sleeve Description",
                    80.99, "KNEE");
        }
    }

    private void initOneSleeve(String brand, String name, String description,
                               Double price, String sleeveType) {

        SleeveEntity baseProduct = new SleeveEntity(brand, name, description, BigDecimal.valueOf(price));

        baseProduct
                .setSleeveType(SleeveTypeEnum.valueOf(sleeveType));

        this.allProductsRepository.save(baseProduct);
    }

    public boolean addSleeve(SleeveAddDTO sleeveAddDTO) {
        if (allProductsRepository.findByName(sleeveAddDTO.getName()).isPresent()) {
            return false;
        }

        SleeveEntity newSleeve = this.productMapper.addSleeveDtoToSleeveEntity(sleeveAddDTO);

        this.allProductsRepository.save(newSleeve);
        return true;
    }
}
