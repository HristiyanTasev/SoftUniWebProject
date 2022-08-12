package bg.softuni.eliteSportsEquipment.service.product;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDetailDTO;
import bg.softuni.eliteSportsEquipment.model.entity.product.*;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SleeveTypeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.StrapTypeEnum;
import bg.softuni.eliteSportsEquipment.model.mapper.ProductMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AllProductsServiceTest {

    @Mock
    private AllProductsRepository mockAllProductsRepo;

    @Mock
    private ProductMapper mockProductMapper;

    @Mock
    AllProductsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new AllProductsService(
                mockAllProductsRepo,
                mockProductMapper
        );
    }

    @Test
    void testGetProductById_WithBeltEntity() {
        BeltEntity belt = new BeltEntity("Brand", "Kolan",
                "Description", new BigDecimal(10));
        belt
                .setSizes(new ArrayList<>())
                .setLeverType(BeltLeverEnum.LEVER)
                .setMaterialType(BeltMaterialEnum.LEATHER)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(1L);

        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO
                .setId(belt.getId())
                .setName(belt.getName())
                .setDescription(belt.getDescription())
                .setPrice(belt.getPrice())
                .setType(belt.getType())
                .setPictureURL(belt.getPicture().getUrl());

        when(mockAllProductsRepo.findById(belt.getId()))
                .thenReturn(Optional.of(belt));

        when(mockProductMapper.productEntityToProductDetailDTO(belt))
                .thenReturn(productDetailDTO);

        ProductDetailDTO testDto = toTest.getProductById(belt.getId());

        Assertions.assertEquals(belt.getLeverType().name().toLowerCase(), testDto.getBeltLeverType().toLowerCase());
        Assertions.assertEquals(belt.getMaterialType().name().toLowerCase(), testDto.getBeltMaterialType().toLowerCase());
    }

    @Test
    void testGetProductById_WithSleeveEntity() {
        SleeveEntity sleeve = new SleeveEntity("Brand", "Sleeve",
                "Description", new BigDecimal(10));
        sleeve
                .setSizes(new ArrayList<>())
                .setSleeveType(SleeveTypeEnum.KNEE)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(1L);

        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO
                .setId(sleeve.getId())
                .setName(sleeve.getName())
                .setDescription(sleeve.getDescription())
                .setPrice(sleeve.getPrice())
                .setType(sleeve.getType())
                .setPictureURL(sleeve.getPicture().getUrl());

        when(mockAllProductsRepo.findById(sleeve.getId()))
                .thenReturn(Optional.of(sleeve));

        when(mockProductMapper.productEntityToProductDetailDTO(sleeve))
                .thenReturn(productDetailDTO);

        ProductDetailDTO testDto = toTest.getProductById(sleeve.getId());

        Assertions.assertEquals(sleeve.getSleeveType().name().toLowerCase() + "s", testDto.getSleeveType().toLowerCase());
    }

    @Test
    void testGetProductById_WithStrapEntity() {
        StrapEntity strap = new StrapEntity("Brand", "Sleeve",
                "Description", new BigDecimal(10));
        strap
                .setStrapType(StrapTypeEnum.LASSO)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(1L);

        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO
                .setId(strap.getId())
                .setName(strap.getName())
                .setDescription(strap.getDescription())
                .setPrice(strap.getPrice())
                .setType(strap.getType())
                .setPictureURL(strap.getPicture().getUrl());

        when(mockAllProductsRepo.findById(strap.getId()))
                .thenReturn(Optional.of(strap));

        when(mockProductMapper.productEntityToProductDetailDTO(strap))
                .thenReturn(productDetailDTO);

        ProductDetailDTO testDto = toTest.getProductById(strap.getId());

        Assertions.assertEquals(strap.getStrapType().name().toLowerCase(), testDto.getStrapType().toLowerCase());
    }

    @Test
    void testGetFeaturedProducts_featuredIsEmpty() {
        StrapEntity strap1 = new StrapEntity("Brand", "Strap1",
                "Description", new BigDecimal(10));
        strap1
                .setStrapType(StrapTypeEnum.LASSO)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(1L);
        StrapEntity strap2 = new StrapEntity("Brand", "Strap2",
                "Description", new BigDecimal(10));
        strap2
                .setStrapType(StrapTypeEnum.LASSO)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(2L);
        StrapEntity strap3 = new StrapEntity("Brand", "Strap3",
                "Description", new BigDecimal(10));
        strap3
                .setStrapType(StrapTypeEnum.LASSO)
                .setPicture(new PictureEntity().setUrl("testUrl"))
                .setId(3L);

        ProductDTO strapDTO1 = new ProductDTO();
        strapDTO1.setId(strap1.getId())
                .setName(strap1.getName())
                .setPrice(strap1.getPrice())
                .setPictureURL(strap1.getPicture().getUrl())
                .setProductType(strap1.getType());

        ProductDTO strapDTO2 = new ProductDTO();
        strapDTO2.setId(strap2.getId())
                .setName(strap2.getName())
                .setPrice(strap2.getPrice())
                .setPictureURL(strap2.getPicture().getUrl())
                .setProductType(strap2.getType());

        ProductDTO strapDTO3 = new ProductDTO();
        strapDTO3.setId(strap3.getId())
                .setName(strap3.getName())
                .setPrice(strap3.getPrice())
                .setPictureURL(strap3.getPicture().getUrl())
                .setProductType(strap3.getType());

        when(mockAllProductsRepo.findAll())
                .thenReturn(List.of(strap1, strap2, strap3));

        when(mockProductMapper.productEntityToProductDTO(strap1))
                .thenReturn(strapDTO1);
        when(mockProductMapper.productEntityToProductDTO(strap2))
                .thenReturn(strapDTO2);
        when(mockProductMapper.productEntityToProductDTO(strap3))
                .thenReturn(strapDTO3);

        List<ProductDTO> featuredProducts = toTest.getFeaturedProducts();

        Assertions.assertEquals(featuredProducts.get(0).getId(), strap1.getId());
        Assertions.assertEquals(featuredProducts.get(1).getId(), strap2.getId());
        Assertions.assertEquals(featuredProducts.get(2).getId(), strap3.getId());
    }
}
