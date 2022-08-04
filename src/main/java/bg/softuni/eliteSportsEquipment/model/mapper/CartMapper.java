package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    CartProductDTO cartProductEntityToCartProductDTO(CartProductEntity cartProductEntity);
}
