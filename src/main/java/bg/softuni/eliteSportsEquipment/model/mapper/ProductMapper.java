package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.BeltAddDTO;
import bg.softuni.eliteSportsEquipment.model.dto.productDTO.ProductDTO;
import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    BeltEntity addBeltDtoToBeltEntity(BeltAddDTO beltAddDTO);

    ProductDTO productEntityToProductDTO(ProductEntity productEntity);
}
