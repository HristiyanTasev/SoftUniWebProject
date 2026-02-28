package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.order.OrderProductDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    OrderProductEntity cartProductEntityToOrderProductEntity(CartProductEntity cartProductEntity);

    @Mapping(target = "pictureURL", source = "product.picture.url")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.price", target = "price")
    OrderProductDTO orderProductEntityToOrderProductDTO(OrderProductEntity orderProductEntity);
}
