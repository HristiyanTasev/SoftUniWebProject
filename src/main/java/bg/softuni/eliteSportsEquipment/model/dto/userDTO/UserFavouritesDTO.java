package bg.softuni.eliteSportsEquipment.model.dto.userDTO;

import java.math.BigDecimal;

public class UserFavouritesDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String pictureURL;

    public UserFavouritesDTO() {
    }

    public UserFavouritesDTO(Long id, String name, BigDecimal price, String pictureURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureURL = pictureURL;
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

    public String getPictureURL() {
        return pictureURL;
    }

    public UserFavouritesDTO setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
        return this;
    }
}
