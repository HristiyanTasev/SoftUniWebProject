package bg.softuni.eliteSportsEquipment.service.user;

import bg.softuni.eliteSportsEquipment.model.dto.UserFavouritesDTO;
import bg.softuni.eliteSportsEquipment.model.entity.FavouriteEntity;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.FavouriteRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FavouriteService {


    private final AllProductsRepository allProductsRepository;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    public FavouriteService(AllProductsRepository allProductsRepository, UserRepository userRepository,
                            FavouriteRepository favouriteRepository) {
        this.allProductsRepository = allProductsRepository;
        this.userRepository = userRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public void init() {
        if (this.favouriteRepository.count() < 1) {
            initFav();
        }
    }

    public void initFav() {
        List<ProductEntity> products = new ArrayList<>(this.allProductsRepository.findAll().subList(0, 3));

        UserEntity user = this.userRepository.findById(1L).orElseThrow();
        FavouriteEntity favs = new FavouriteEntity(products);

        user.setFavourites(favs);
        this.userRepository.save(user);
    }

    public List<UserFavouritesDTO> getFavProductsByEmail(String email) {
        UserEntity user = this.userRepository.findByEmail(email).orElseThrow();

        FavouriteEntity favourites = user.getFavourites();
        List<ProductEntity> favProducts;

        if (favourites != null) {
            favProducts = favourites.getProducts();
            List<UserFavouritesDTO> favProductsView = new ArrayList<>();

            if (favProducts.size() >= 1) {
                favProductsView = favProducts
                        .stream()
                        .map(p -> new UserFavouritesDTO(p.getId(), p.getName(), p.getPrice()))
                        .collect(Collectors.toList());
            }

            return favProductsView;
        }

        return null;
    }
}
