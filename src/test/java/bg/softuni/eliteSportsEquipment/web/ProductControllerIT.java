package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.entity.product.BeltEntity;
import bg.softuni.eliteSportsEquipment.model.entity.user.UserEntity;
import bg.softuni.eliteSportsEquipment.model.user.AppUserDetails;
import bg.softuni.eliteSportsEquipment.service.cloudinary.CloudinaryService;
import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import bg.softuni.eliteSportsEquipment.service.product.PictureService;
import bg.softuni.eliteSportsEquipment.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private Pageable mockPageable;

    @MockBean
    private AllProductsService mockAllProductsService;

    @MockBean
    private CloudinaryService mockCloudinary;

    @MockBean
    private PictureService mockPicService;

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
    @WithMockUser(
            username = "admin@mail.com",
            roles = {}
    )
    void testAllProducts() throws Exception {
        Pageable mockPageable = this.mockPageable;

        mockMvc.perform(get("/products/all")
                        .flashAttr("products", mockAllProductsService.getAllProducts(mockPageable)))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    void testGetAddBeltByUser_isForbidden() throws Exception {
        mockMvc.perform(get("/products/add/belt")
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
    void testGetAddBeltByAdmin() throws Exception {
        mockMvc.perform(get("/products/add/belt")
                        .with(user(testDataUtils.getAdmin()))
                        .param("brand", "testBrand").
                        param("name", "testName").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "LEATHER").
                        param("leverType", "PRONG"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-belt"));
    }

    @Test
    void testAddBeltByUser_isForbidden() throws Exception {
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
    void testAddBeltByModerator_isForbidden() throws Exception {
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
    void testAddBeltByAdmin_WithWrongInput() throws Exception {

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
    void testAddBeltByAdmin() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();

        MockMultipartFile picture = new MockMultipartFile("picture", "test.webp",
                "image/webp", "Spring Framework".getBytes());

        mockMvc.perform(multipart("/products/add/belt")
                        .file(picture)
                        .param("brand", "TEST")
                        .param("brand", "TEST")
                        .param("name", "TEST")
                        .param("description", "testDescription")
                        .param("price", "20")
                        .param("materialType", "LEATHER")
                        .param("leverType", "PRONG")
                        .with(csrf())
                        .with(user(admin)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/all"));
    }

    @Test
    void testGetAddStrapByUser_isForbidden() throws Exception {
        mockMvc.perform(get("/products/add/strap")
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
    void testGetAddStrapByAdmin() throws Exception {
        mockMvc.perform(get("/products/add/strap")
                        .with(user(testDataUtils.getAdmin()))
                        .param("brand", "testBrand").
                        param("name", "testName").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "LEATHER").
                        param("leverType", "PRONG"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-strap"));
    }

    @Test
    void testAddStrapByUser_isForbidden() throws Exception {
        mockMvc.perform(post("/products/add/strap")
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
    void testAddStrapByModerator_isForbidden() throws Exception {
        mockMvc.perform(post("/products/add/strap")
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
    void testAddStrapByAdmin_WithWrongInput() throws Exception {

        AppUserDetails admin = testDataUtils.getAdmin();
        mockMvc.perform(post("/products/add/strap")
                        .with(csrf())
                        .with(user(admin)).
                        param("brand", "").
                        param("name", "").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "asd").
                        param("leverType", "PRONG"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/add/strap"));
    }

    @Test
    void testAddStrapByAdmin() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();

        MockMultipartFile picture = new MockMultipartFile("picture", "test.webp",
                "image/webp", "Spring Framework".getBytes());

        mockMvc.perform(multipart("/products/add/strap")
                        .file(picture)
                        .param("brand", "TEST")
                        .param("brand", "TEST")
                        .param("name", "TEST")
                        .param("description", "testDescription")
                        .param("price", "20")
                        .param("strapType", "LASSO")
                        .with(csrf())
                        .with(user(admin)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/all"));
    }

    @Test
    void testGetAddSleeveByUser_isForbidden() throws Exception {
        mockMvc.perform(get("/products/add/sleeve")
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
    void testGetAddSleeveByAdmin() throws Exception {
        mockMvc.perform(get("/products/add/sleeve")
                        .with(user(testDataUtils.getAdmin()))
                        .param("brand", "testBrand").
                        param("name", "testName").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "LEATHER").
                        param("leverType", "PRONG"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-sleeve"));
    }

    @Test
    void testAddSleeveByUser_isForbidden() throws Exception {
        mockMvc.perform(post("/products/add/sleeve")
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
    void testAddSleeveByModerator_isForbidden() throws Exception {
        mockMvc.perform(post("/products/add/sleeve")
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
    void testAddSleeveByAdmin_WithWrongInput() throws Exception {

        AppUserDetails admin = testDataUtils.getAdmin();
        mockMvc.perform(post("/products/add/sleeve")
                        .with(csrf())
                        .with(user(admin)).
                        param("brand", "").
                        param("name", "").
                        param("description", "testDescription").
                        param("price", "20").
                        param("materialType", "asd").
                        param("leverType", "PRONG"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/add/sleeve"));
    }

    @Test
    void testAddSleeveByAdmin() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();

        MockMultipartFile picture = new MockMultipartFile("picture", "test.webp",
                "image/webp", "Spring Framework".getBytes());

        mockMvc.perform(multipart("/products/add/sleeve")
                        .file(picture)
                        .param("brand", "TEST")
                        .param("brand", "TEST")
                        .param("name", "TEST")
                        .param("description", "testDescription")
                        .param("price", "20")
                        .param("sleeveType", "ELBOW")
                        .with(csrf())
                        .with(user(admin)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/all"));
    }

//    @Test
//    void testGetProductDetails() throws Exception {
//        BeltEntity belt = testDataUtils.initBelt("Name", 1L);
//        mockMvc.perform(get("/products/details/{id}", belt.getId())
//                        .with(user(testDataUtils.getUser())))
//                .andExpect(status().isOk())
//                .andExpect(view().name("product-details"));
//    }
}
