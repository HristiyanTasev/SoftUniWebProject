package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import java.math.BigDecimal;

public class UserFavouritesDTO {

    private Long id;
    // TODO add picture when cloudinary gets implemented
    private String name;
    private BigDecimal price;

    public UserFavouritesDTO() {
    }

    public UserFavouritesDTO(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public UserFavouritesDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserFavouritesDTO setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UserFavouritesDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
