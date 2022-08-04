package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.mapper.CartMapper;
import bg.softuni.eliteSportsEquipment.repository.AllProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.CartProductsRepository;
import bg.softuni.eliteSportsEquipment.repository.CartRepository;
import bg.softuni.eliteSportsEquipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final AllProductsRepository allProductsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final CartMapper cartMapper;

    public CartService(AllProductsRepository allProductsRepository, UserRepository userRepository,
                       CartRepository cartRepository, CartProductsRepository cartProductsRepository,
                       CartMapper cartMapper) {
        this.allProductsRepository = allProductsRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartProductsRepository = cartProductsRepository;
        this.cartMapper = cartMapper;
    }

    public void init() {
        if (this.cartRepository.count() < 1) {
            initCart();
        }
    }

    public void initCart() {
        List<ProductEntity> products = this.allProductsRepository.findAll().subList(0, 4);
        List<CartProductEntity> cartProducts = new ArrayList<>();

        cartProducts.add(new CartProductEntity().setProduct(products.get(0)).setProductQuantity(1));
        cartProducts.add(new CartProductEntity().setProduct(products.get(1)).setProductQuantity(2));
        cartProducts.add(new CartProductEntity().setProduct(products.get(2)).setProductQuantity(3));
        cartProducts.add(new CartProductEntity().setProduct(products.get(3)).setProductQuantity(1));

        UserEntity user = this.userRepository.findById(1L).orElseThrow();
        CartEntity cart = new CartEntity(cartProducts);

        user.setCart(cart);
        this.userRepository.save(user);
    }

    public CartDTO getCartByUserEmail(String email) {
        UserEntity currentUser = this.userRepository.findByEmail(email).orElseThrow();
        CartEntity cartEntity = currentUser.getCart();

        if (cartEntity == null) {
            return null;
        } else {
            CartDTO cart = new CartDTO();

            cart.setCartProducts(cartEntity
                    .getCartProducts()
                    .stream()
                    .map(cP -> {
                        CartProductDTO cartProductDTO = cartMapper.cartProductEntityToCartProductDTO(cP);
                        cartProductDTO
                                .setTotalPrice(cP.getProduct().getPrice()
                                        .multiply(new BigDecimal(cP.getProductQuantity())));
                        cart.setSubTotal(cart.getSubTotal().add(cartProductDTO.getTotalPrice()));

                        return cartProductDTO;
                    })
                    .collect(Collectors.toList()));

            return cart;
        }
    }

    public void addProductToCartById(Long productId, String email) {
        UserEntity currentUser = this.userRepository.findByEmail(email).orElseThrow();
        ProductEntity productToAdd = this.allProductsRepository.findById(productId).orElseThrow();
        CartEntity userCart = currentUser.getCart();

        if (userCart == null) {
            userCart = new CartEntity();
            currentUser.setCart(userCart);
        }

        List<CartProductEntity> cartProducts = userCart.getCartProducts();

        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }

        Optional<CartProductEntity> hasProduct = cartProducts
                .stream()
                .filter(p -> p.getProduct()
                        .equals(productToAdd))
                .findFirst();

        hasProduct.ifPresent(cP -> {
            cP.setProductQuantity(cP.getProductQuantity() + 1);
//            cartProducts.stream().filter(p -> p.getProduct().getName().equals(cP.getProduct().getName()))
        });

        if (hasProduct.isEmpty()) {
            cartProducts.add(new CartProductEntity().setProduct(productToAdd).setProductQuantity(1));
            userCart.setCartProducts(cartProducts);
        }


        this.userRepository.save(currentUser);
    }

    public void updateProductQuantity() {

    }
}
