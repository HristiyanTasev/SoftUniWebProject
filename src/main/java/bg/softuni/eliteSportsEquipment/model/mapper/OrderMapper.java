package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.OrderProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderProductEntity cartProductEntityToOrderProductEntity(CartProductEntity cartProductEntity);
}
