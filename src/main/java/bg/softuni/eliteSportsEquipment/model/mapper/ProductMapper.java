package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.*;
import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.SleeveEntity;
import bg.softuni.eliteSportsEquipment.model.entity.StrapEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    BeltEntity addBeltDtoToBeltEntity(BeltAddDTO beltAddDTO);

    ProductDTO productEntityToProductDTO(ProductEntity productEntity);

    ProductDetailDTO productEntityToProductDetailDTO(ProductEntity productEntity);

    StrapEntity addStrapDtoToStrapEntity(StrapAddDTO strapAddDTO);

    SleeveEntity addSleeveDtoToSleeveEntity(SleeveAddDTO sleeveAddDTO);
}
