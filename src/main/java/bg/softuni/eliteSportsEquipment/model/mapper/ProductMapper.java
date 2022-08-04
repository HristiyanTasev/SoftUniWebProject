package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.*;
import bg.softuni.eliteSportsEquipment.model.entity.product.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.StrapEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    BeltEntity addBeltDtoToBeltEntity(BeltAddDTO beltAddDTO);

    ProductDTO productEntityToProductDTO(ProductEntity productEntity);

    ProductDetailDTO productEntityToProductDetailDTO(ProductEntity productEntity);

    StrapEntity addStrapDtoToStrapEntity(StrapAddDTO strapAddDTO);

    SleeveEntity addSleeveDtoToSleeveEntity(SleeveAddDTO sleeveAddDTO);
}
