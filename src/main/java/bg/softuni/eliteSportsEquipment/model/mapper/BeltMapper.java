package bg.softuni.eliteSportsEquipment.model.mapper;

import bg.softuni.eliteSportsEquipment.model.dto.productDTO.BeltAddDTO;
import bg.softuni.eliteSportsEquipment.model.entity.BeltEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeltMapper {

    BeltEntity addBeltDtoToBeltEntity(BeltAddDTO beltAddDTO);
//    OfferEntity createOrUpdateOfferDtoToOfferEntity(CreateOrUpdateOfferDTO addOfferDTO);
//
//    CreateOrUpdateOfferDTO offerEntityToCreateOrUpdateOfferDtoTo(OfferEntity offerEntity);
//
//    @Mapping(source = "model.name", target = "model")
//    @Mapping(source = "model.brand.name", target = "brand")
//    @Mapping(source = "seller.firstName", target = "sellerFirstName")
//    @Mapping(source = "seller.lastName", target = "sellerLastName")
//    OfferDetailDTO offerEntityToOfferDetailDto(OfferEntity offerEntity);
}
