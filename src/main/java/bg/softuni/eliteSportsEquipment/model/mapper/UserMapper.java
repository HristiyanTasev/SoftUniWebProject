package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.userDTO.UserRegisterDTO;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity userDtoToUserEntity(UserRegisterDTO registerDTO);
}
