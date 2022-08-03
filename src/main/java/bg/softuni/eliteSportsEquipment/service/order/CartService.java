package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.dto.CartDTO;
import bg.softuni.eliteSportsEquipment.model.entity.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.UserEntity;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.CartRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {

    private final AllProductsRepository allProductsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartService(AllProductsRepository allProductsRepository, UserRepository userRepository,
                       CartRepository cartRepository) {
        this.allProductsRepository = allProductsRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void init() {
        if (this.cartRepository.count() < 1) {
            initCart();
        }
    }

    public void initCart() {
        List<ProductEntity> products = this.allProductsRepository.findAll().subList(0, 4);

        UserEntity user = this.userRepository.findById(1L).orElseThrow();
        CartEntity cart = new CartEntity(products);

        user.setCart(cart);
        this.userRepository.save(user);
    }

    public void addProductToCartById(Long productId, String name) {
        UserEntity currentUser = this.userRepository.findByEmail(name).orElseThrow();
        ProductEntity productToAdd = this.allProductsRepository.findById(productId).orElseThrow();

        if (currentUser.getCart() == null) {
            currentUser.setCart(new CartEntity());
        }

        currentUser.getCart().getProducts().add(productToAdd);
        this.userRepository.save(currentUser);
    }

    public CartDTO getCartByUserEmail(String email) {
        return null;
    }
}
