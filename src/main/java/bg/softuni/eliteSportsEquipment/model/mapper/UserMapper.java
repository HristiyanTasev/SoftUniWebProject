package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userDtoToUserEntity(UserRegisterDTO registerDTO);
}
