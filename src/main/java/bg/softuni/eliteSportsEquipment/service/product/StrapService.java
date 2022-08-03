package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.StrapAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.StrapEntity;
import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StrapService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;

    public StrapService(AllProductsRepository allProductsRepository, ProductMapper productMapper) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("strap") < 1) {
            initOneStrap("SBD", "Strap", "Long Strap Description",
                    40.99, "CLOSED_LOOP");

            initOneStrap("ESE", "Strap2", "2Long Strap Description",
                    60.99, "CLOSED_LOOP");

            initOneStrap("SBD", "Strap3", "Long Strap Description",
                    70.99, "LASSO");

            initOneStrap("SBD", "Strap4", "Long Strap Description",
                    80.99, "FIGURE_EIGHT");
        }
    }

    private void initOneStrap(String brand, String name, String description,
                              Double price, String strapType) {

        StrapEntity baseProduct = new StrapEntity(brand, name, description, BigDecimal.valueOf(price));

        baseProduct
                .setStrapType(StrapTypeEnum.valueOf(strapType));

        this.allProductsRepository.save(baseProduct);
    }

    public boolean addStrap(StrapAddDTO strapAddDTO) {
        if (allProductsRepository.findByName(strapAddDTO.getName()).isPresent()) {
            return false;
        }

        StrapEntity newStrap = this.productMapper.addStrapDtoToStrapEntity(strapAddDTO);

        this.allProductsRepository.save(newStrap);
        return true;
    }
}