package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.SleeveAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.PictureEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class SleeveService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    public SleeveService(AllProductsRepository allProductsRepository, ProductMapper productMapper,
                         PictureService pictureService, PictureRepository pictureRepository) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("sleeve") < 1) {
            initOneSleeve("SBD", "Sleeve", "Long Sleeve Description",
                    40.99, "ELBOW");

            initOneSleeve("ESE", "Sleeve2", "2Long Sleeve Description",
                    60.99, "KNEE");

            initOneSleeve("SBD", "Sleeve3", "Long Sleeve Description",
                    70.99, "ELBOW");

            initOneSleeve("ESE", "Sleeve4", "Long Sleeve Description",
                    80.99, "KNEE");

            initOneSleeve("SBD", "Sleeve5", "Long Sleeve Description",
                    80.99, "KNEE");

            initOneSleeve("PR", "Sleeve6", "Long Sleeve Description",
                    80.99, "ELBOW");

            initOneSleeve("PR", "Sleeve7", "Long Sleeve Description",
                    60.00, "KNEE");

            initOneSleeve("SBD", "Sleeve8", "Long Sleeve Description",
                    100.00, "KNEE");
        }
    }

    private void initOneSleeve(String brand, String name, String description,
                               Double price, String sleeveType) {

        SleeveEntity baseProduct = new SleeveEntity(brand, name, description, BigDecimal.valueOf(price));

        PictureEntity pic = new PictureEntity()
                .setUrl("https://res.cloudinary.com/djoiyj8ia/image/upload/v1659890149/elbow_sleeve_l2pfsu.png")
                .setPublicId("elbow_sleeve_l2pfsu");

        baseProduct
                .setSleeveType(SleeveTypeEnum.valueOf(sleeveType))
                .setPicture(pic);

        this.pictureRepository.save(pic);
        this.allProductsRepository.save(baseProduct);
    }

    public boolean addSleeve(SleeveAddDTO sleeveAddDTO) {
        if (allProductsRepository.findByName(sleeveAddDTO.getName()).isPresent()) {
            return false;
        }

        SleeveEntity newSleeve = this.productMapper.addSleeveDtoToSleeveEntity(sleeveAddDTO);

        try {
            PictureEntity picture = this.pictureService.createPictureEntity(sleeveAddDTO.getPicture());
            newSleeve.setPicture(picture);
        } catch (IOException e) {
            return false;
        }

        this.allProductsRepository.save(newSleeve);
        return true;
    }
}
