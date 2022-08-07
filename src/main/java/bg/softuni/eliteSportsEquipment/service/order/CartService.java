package bg.softuni.eliteSportsEquipment.service.order;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartUpdateDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
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

        CartEntity cart = new CartEntity();

        cartProducts.add(new CartProductEntity().setProduct(products.get(0)).setProductQuantity(1).setSize(SizeEnum.M));
        cartProducts.add(new CartProductEntity().setProduct(products.get(1)).setProductQuantity(2).setSize(SizeEnum.L));
        cartProducts.add(new CartProductEntity().setProduct(products.get(2)).setProductQuantity(3).setSize(SizeEnum.S));
        cartProducts.add(new CartProductEntity().setProduct(products.get(3)).setProductQuantity(1).setSize(SizeEnum.XS));

        cart.setCartProducts(cartProducts);
        UserEntity user = this.userRepository.findById(1L).orElseThrow();
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
                        cartProductDTO.setPictureURL(cP.getProduct().getPicture().getUrl());
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

    public void addProductToCartById(Long productId, String email, String size) {
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
                .filter(p -> {
                    boolean product = p.getProduct()
                            .equals(productToAdd);
                    boolean hasSize = p.getSize().name().equals(size);

                    return product && hasSize;
                })
                .findFirst();

        hasProduct.ifPresent(cP ->
                cP.setProductQuantity(cP.getProductQuantity() + 1)
        );

        if (hasProduct.isEmpty()) {
            cartProducts.add(new CartProductEntity()
                    .setProduct(productToAdd)
                    .setProductQuantity(1)
                    .setSize(size != null ? SizeEnum.valueOf(size) : null));

            userCart.setCartProducts(cartProducts);
        }


        this.userRepository.save(currentUser);
    }

    public void updateProductQuantity(Long id, String email) {

    }

    public void deleteProductFromCartById(Long id, String email) {
        CartEntity cart = this.userRepository.findByEmail(email).orElseThrow().getCart();
        Optional<CartProductEntity> productToRemove = this.cartProductsRepository.findById(id);

        if (cart != null && productToRemove.isPresent()) {
            cart.getCartProducts().remove(productToRemove.get());
            this.cartRepository.save(cart);
            this.cartProductsRepository.deleteById(id);
        }

    }

    public void updateCart(CartUpdateDTO cartUpdateDTO) {

    }
}
