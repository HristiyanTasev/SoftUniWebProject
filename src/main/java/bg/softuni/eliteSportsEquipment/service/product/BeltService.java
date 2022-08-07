package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.BeltAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.PictureEntity;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class BeltService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    public BeltService(AllProductsRepository allProductsRepository, ProductMapper productMapper,
                       PictureService pictureService, PictureRepository pictureRepository) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
        this.pictureService = pictureService;
        this.pictureRepository = pictureRepository;
    }

    public void init() {
        if (allProductsRepository.findCountProductsByType("belt") < 1) {
            initOneBelt("SBD", "Kolan", "Long Belt Description",
                    40.99, "LEATHER", "PRONG");

            initOneBelt("ESE", "Kolan2", "2Long Belt Description",
                    60.99, "LEATHER", "LEVER");

            initOneBelt("SBD", "Kolan3", """
                            Manufactured with high grade 7mm neoprene, designed to minimise the risk of
                                            injury and to aid
                                            performance. Relied on by strength athletes around the world, these 7mm knee sleeves are ideal
                                            for powerlifting and heavy training.""",
                    70.99, "LEATHER", "LEVER");

            initOneBelt("ESE", "Kolan4", "Long Belt Description",
                    20.99, "NYLON", "LEVER");

            initOneBelt("ESE", "Kolan5", "Long Belt Description",
                    200.99, "LEATHER", "PRONG");

            initOneBelt("PR", "Kolan6", "Long Belt Description",
                    140.99, "NYLON", "PRONG");

            initOneBelt("SBD", "Kolan7", "Long Belt Description",
                    100.99, "LEATHER", "LEVER");

            initOneBelt("SBD", "Kolan8", "Long Belt Description",
                    110.99, "NYLON", "PRONG");

        }
    }

    public void initOneBelt(String brand, String name, String description,
                            Double price, String materialType,
                            String leverType) {

        BeltEntity baseProduct = new BeltEntity(brand, name, description, BigDecimal.valueOf(price));

        PictureEntity pic = new PictureEntity()
                .setUrl("https://res.cloudinary.com/djoiyj8ia/image/upload/v1659890181/prong_belt_xfgtgg.png")
                .setPublicId("prong_belt_xfgtgg");

        baseProduct
                .setMaterialType(BeltMaterialEnum.valueOf(materialType))
                .setLeverType(BeltLeverEnum.valueOf(leverType))
                .setPicture(pic);

        this.pictureRepository.save(pic);
        this.allProductsRepository.save(baseProduct);
    }

    public boolean addBelt(BeltAddDTO beltAddDTO) {
        if (allProductsRepository.findByName(beltAddDTO.getName()).isPresent()) {
            return false;
        }

        BeltEntity newBelt = this.productMapper.addBeltDtoToBeltEntity(beltAddDTO);

        try {
            PictureEntity picture = this.pictureService.createPictureEntity(beltAddDTO.getPicture());
            newBelt.setPicture(picture);
        } catch (IOException e) {
            return false;
        }

        this.allProductsRepository.save(newBelt);
        return true;
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
