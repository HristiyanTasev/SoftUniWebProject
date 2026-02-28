package bg.softuni.eliteSportsEquipment.util;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.PictureEntity;
import bg.softuni.eliteSportsEquipment.model.entity.product.ProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserRoleEntity;
import bg.softuni.eliteSportsEquipment.model.enums.BeltLeverEnum;
import bg.softuni.eliteSportsEquipment.model.enums.BeltMaterialEnum;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.model.enums.UserRoleEnum;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import bg.softuni.eliteSportsEquipment.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TestDataUtils {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PictureRepository pictureRepository;
    private final AllProductsRepository allProductsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final FavouriteRepository favouriteRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final CartProductsRepository cartProductsRepository;

    public TestDataUtils(UserRepository userRepository,
                         UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder,
                         PictureRepository pictureRepository, AllProductsRepository allProductsRepository,
                         CartRepository cartRepository, OrderRepository orderRepository,
                         FavouriteRepository favouriteRepository, OrderProductsRepository orderProductsRepository, CartProductsRepository cartProductsRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.pictureRepository = pictureRepository;
        this.allProductsRepository = allProductsRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.favouriteRepository = favouriteRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.cartProductsRepository = cartProductsRepository;
    }

    public AppUserDetails getAdmin() {
        return new AppUserDetails("asdasd",
                "admin@mail.com",
                "Admin",
                "Adminov",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_MODERATOR")));
    }

    public AppUserDetails getModerator() {
        return new AppUserDetails("asdasd",
                "admin@mail.com",
                "Admin",
                "Adminov",
                List.of(new SimpleGrantedAuthority("ROLE_MODERATOR")));
    }

    public AppUserDetails getUser() {
        return new AppUserDetails("asdasd",
                "admin@mail.com",
                "Admin",
                "Adminov",
                List.of());
    }

    private void initRoles() {
        if (this.userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            this.userRoleRepository.save(adminRole);
            this.userRoleRepository.save(moderatorRole);
        }
    }

    public UserEntity initAdmin(String email) {

        initRoles();

        UserEntity admin = new UserEntity()
                .setRoles(new HashSet<>(this.userRoleRepository.findAll()))
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setEmail(email)
                .setAddress("Grad", "Adres")
                .setPassword(passwordEncoder.encode("asdasd"));

        return this.userRepository.save(admin);
    }

    public UserEntity initModerator(String email) {

        initRoles();

        UserEntity moderator = new UserEntity()
                .setRoles(Set.of(this.userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR).get()))
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail(email)
                .setAddress("Grad", "Adres")
                .setPassword(passwordEncoder.encode("asdasd"));

        return this.userRepository.save(moderator);
    }

    public UserEntity initUser(String email) {
        UserEntity user = new UserEntity()
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail(email)
                .setAddress("Grad", "Adres")
                .setPassword(passwordEncoder.encode("asdasd"));

        return this.userRepository.save(user);
    }

    public CartEntity initCart(UserEntity user) {
        List<ProductEntity> products = new ArrayList<>();
        products.add(initBelt("Kolan"));
//        products.add(initBelt("Kolan", 2L));
//        products.add(initBelt("Kolan", 3L));
//        products.add(initBelt("Kolan", 4L));

        List<CartProductEntity> cartProducts = new ArrayList<>();

        CartEntity cart = new CartEntity();

        cartProducts.add(new CartProductEntity().setProduct(products.get(0)).setProductQuantity(1).setSize(SizeEnum.M));
//        cartProducts.add(new CartProductEntity().setProduct(products.get(1)).setProductQuantity(2).setSize(SizeEnum.L));
//        cartProducts.add(new CartProductEntity().setProduct(products.get(2)).setProductQuantity(3).setSize(SizeEnum.S));
//        cartProducts.add(new CartProductEntity().setProduct(products.get(3)).setProductQuantity(1).setSize(SizeEnum.XS));

        cart.setCartProducts(cartProducts);
//        cart.setId(1L);
        cart.setUser(user);
        user.setCart(cart);

        this.cartRepository.save(cart);
        this.userRepository.save(user);

        return cart;
    }

    public BeltEntity initBelt(String name) {
        BeltEntity baseProduct = new BeltEntity("SBD", name, "Long Belt Description",
                BigDecimal.valueOf(40.99));
//        baseProduct.setId(id);
        baseProduct.setSizes(new ArrayList<>());

        PictureEntity pic = new PictureEntity()
                .setUrl("https://res.cloudinary.com/djoiyj8ia/image/upload/v1659890181/prong_belt_xfgtgg.png")
                .setPublicId("prong_belt_xfgtgg");

        baseProduct
                .setMaterialType(BeltMaterialEnum.valueOf("LEATHER"))
                .setLeverType(BeltLeverEnum.valueOf("PRONG"))
                .setPicture(pic);

        this.pictureRepository.save(pic);
        this.allProductsRepository.save(baseProduct);

        return baseProduct;
    }

    @Transactional
    public void cleanUpDatabase() {
        // Break User<->Cart one-to-one both ways
        this.userRepository.findAll().forEach(user -> user.setCart(null));
        this.cartRepository.findAll().forEach(cart -> cart.setUser(null));

        // Clear join-table relationships
        this.orderRepository.findAll().forEach(order -> order.getOrderProducts().clear());
        this.cartRepository.findAll().forEach(cart -> cart.getCartProducts().clear());
        this.favouriteRepository.findAll().forEach(fav -> fav.getProducts().clear());
        this.userRepository.findAll().forEach(user -> {
            user.getRoles().clear();
            user.getOrders().clear();
        });

        // Flush relationship clears if needed
        this.userRepository.flush();
        this.cartRepository.flush();
        this.orderRepository.flush();
        this.favouriteRepository.flush();

        // Delete leaf tables first
        this.orderProductsRepository.deleteAll();
        this.cartProductsRepository.deleteAll();

        // Delete middle tables
        this.orderRepository.deleteAll();
        this.favouriteRepository.deleteAll();
        this.cartRepository.deleteAll();

        // Delete products then pictures
        this.allProductsRepository.deleteAll();
        this.pictureRepository.deleteAll();

        // Delete users then roles
        this.userRepository.deleteAll();
        this.userRoleRepository.deleteAll();
    }
}
