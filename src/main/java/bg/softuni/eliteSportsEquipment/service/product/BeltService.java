package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.BeltAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BeltService {

    private final AllProductsRepository allProductsRepository;
    private final ProductMapper productMapper;

    public BeltService(AllProductsRepository allProductsRepository, ProductMapper productMapper) {
        this.allProductsRepository = allProductsRepository;
        this.productMapper = productMapper;
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

            initOneBelt("SBD", "Kolan4", "Long Belt Description",
                    80.99, "NYLON", "PRONG");
        }
    }

    private void initOneBelt(String brand, String name, String description,
                             Double price, String materialType,
                             String leverType) {

        BeltEntity baseProduct = new BeltEntity(brand, name, description, BigDecimal.valueOf(price));

        baseProduct
                .setMaterialType(BeltMaterialEnum.valueOf(materialType))
                .setLeverType(BeltLeverEnum.valueOf(leverType));

        this.allProductsRepository.save(baseProduct);
    }

    public boolean addBelt(BeltAddDTO beltAddDTO) {
        if (allProductsRepository.findByName(beltAddDTO.getName()).isPresent()) {
            return false;
        }

        BeltEntity newBelt = this.productMapper.addBeltDtoToBeltEntity(beltAddDTO);

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
