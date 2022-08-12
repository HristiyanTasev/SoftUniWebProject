package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.entity.order.CartEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
//        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithUserDetails(
            value = "user@example.com",
            userDetailsServiceBeanName = "testUserDataService"
    )
    void testGetUserCart() throws Exception {
        mockMvc.perform(get("/users/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }
}
