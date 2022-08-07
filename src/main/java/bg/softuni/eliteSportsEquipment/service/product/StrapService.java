package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.StrapAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.PictureEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.StrapEntity;
import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class StrapService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    public StrapService(AllProductsRepository allProductsRepository, ProductMapper productMapper,
                        PictureService pictureService, PictureRepository pictureRepository) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
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

        PictureEntity pic = new PictureEntity()
                .setUrl("https://res.cloudinary.com/djoiyj8ia/image/upload/v1659890193/straps_y6s8eh.png")
                .setPublicId("straps_y6s8eh");

        baseProduct
                .setStrapType(StrapTypeEnum.valueOf(strapType))
                .setPicture(pic);

        this.pictureRepository.save(pic);
        this.allProductsRepository.save(baseProduct);
    }

    public boolean addStrap(StrapAddDTO strapAddDTO) {
        if (allProductsRepository.findByName(strapAddDTO.getName()).isPresent()) {
            return false;
        }

        StrapEntity newStrap = this.productMapper.addStrapDtoToStrapEntity(strapAddDTO);

        try {
            PictureEntity picture = this.pictureService.createPictureEntity(strapAddDTO.getPicture());
            newStrap.setPicture(picture);
        } catch (IOException e) {
            return false;
        }

        this.allProductsRepository.save(newStrap);
        return true;
    }
}