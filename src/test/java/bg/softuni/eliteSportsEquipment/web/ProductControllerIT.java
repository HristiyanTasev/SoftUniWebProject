package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import bg.softuni.eliteSportsEquipment.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.param;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private MockMultipartFile mockMultipartFile;

    private UserEntity testAdmin, testModerator, testUser;

    @BeforeEach
    void setUp() {
        testAdmin = testDataUtils.initAdmin("admin@mail.com");
        testModerator = testDataUtils.initModerator("moderator@mail.com");
        testUser = testDataUtils.initUser("user@mail.com");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testAllProducts() throws Exception {
        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    void testAddProductByUser() throws Exception {
        mockMvc.perform(post("/products/add/belt")
                        .with(csrf())
                        .with(user(testDataUtils.getUser()))
                        .param("brand", "testBrand").
                        param("name", "testName").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "LEATHER").
                        param("leverType", "PRONG"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAddProductByModerator() throws Exception {
        mockMvc.perform(post("/products/add/belt")
                        .with(csrf())
                        .with(user(testDataUtils.getModerator()))
                        .param("brand", "testBrand").
                        param("name", "testName").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "LEATHER").
                        param("leverType", "PRONG"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testAddProductByAdmin_WithWrongInput() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();
        mockMvc.perform(post("/products/add/belt")
                        .with(csrf())
                        .with(user(admin)).
                        param("brand", "").
                        param("name", "").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "asd").
                        param("leverType", "PRONG"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/add/belt"));
    }

    @Test
    void testAddProductByAdmin() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();

        mockMvc.perform(post("/products/add/belt")
                        .with(csrf())
                        .with(user(admin))
                        .param("brand", "TEST")
                        .param("name", "TEST")
                        .param("description", "testDescription")
                        .param("price", "20")
                        .param("materialType", "LEATHER")
                        .param("leverType", "PRONG")) // TODO: add picture param with multipart file for test to pass
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/all"));
    }
}
