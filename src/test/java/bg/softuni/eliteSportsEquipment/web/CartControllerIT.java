package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.order.CartDTO;
import bg.softuni.eliteSportsEquipment.model.dto.order.CartProductDTO;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.order.CartProductEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.enums.SizeEnum;
import bg.softuni.eliteSportsEquipment.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private UserEntity testUser;
    private CartEntity testCart;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.initUser("user@mail.com");
        testCart = testDataUtils.initCart(testUser);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithUserDetails(
            value = "user@mail.com",
            userDetailsServiceBeanName = "testUserDataService")
    void testGetUserCart() throws Exception {
        mockMvc.perform(get("/users/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }

    @Test
    void testGetUserCart_isAnonymous() throws Exception {
        mockMvc.perform(get("/users/cart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(
            value = "user@mail.com",
            userDetailsServiceBeanName = "testUserDataService")
    void testAddToUserCart() throws Exception {
        CartEntity cart = testUser.getCart();
        mockMvc.perform(post("/users/cart/add/{id}", cart.getId())
                        .with(csrf())
                        .param("size", SizeEnum.S.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/cart"));
    }

    @Test
    void testAddToCart_isAnonymous() throws Exception {
        mockMvc.perform(post("/users/cart/add/{id}", 1L).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(
            value = "user@mail.com",
            userDetailsServiceBeanName = "testUserDataService")
    void testDeleteFromUserCart() throws Exception {
        CartEntity cart = testUser.getCart();
        mockMvc.perform(delete("/users/cart/delete/{id}", cart.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/cart"));
    }

    @Test
    void testRemoveFromCart_isAnonymous() throws Exception {
        mockMvc.perform(delete("/users/cart/add/{id}", 1L).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithUserDetails(
            value = "user@mail.com",
            userDetailsServiceBeanName = "testUserDataService")
    void testUpdateUserCart() throws Exception {
        List<CartProductEntity> cartProducts = testUser.getCart().getCartProducts();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartProducts(
                cartProducts
                        .stream()
                        .map(cP -> new CartProductDTO().setProductQuantity(cP.getProductQuantity() + 1))
                        .collect(Collectors.toList()));

//        mockMvc.perform(patch("/users/cart/update")
//                        .with(csrf())
//                        .content())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/cart"));
    }
}
