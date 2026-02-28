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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ModerationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

//    @MockBean
//    private UserService mockUserService;

    private UserEntity testAdmin, testModerator, testUser;


    @BeforeEach
    void setUp() {
        testDataUtils.cleanUpDatabase();
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
            roles = {"ADMIN", "MODERATOR"}
    )
    void testGetServicePage_availableForAdmin() throws Exception {
        AppUserDetails admin = testDataUtils.getAdmin();

        mockMvc.perform(get("/service/users")
                        .flashAttr("users", List.of())
                        .flashAttr("currentUser", admin.getUsername()))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    @WithMockUser(
            username = "user@mail.com",
            roles = {}
    )
    void testGetServicePage_notAvailableForUser() throws Exception {
        AppUserDetails user = testDataUtils.getUser();

        mockMvc.perform(get("/service/users")
                        .flashAttr("users", List.of())
                        .flashAttr("currentUser", user.getUsername()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(
            username = "moderator@mail.com",
            roles = {"MODERATOR"}
    )
    void testGetServicePage_notAvailableForModerator() throws Exception {
        AppUserDetails user = testDataUtils.getModerator();

        mockMvc.perform(get("/service/users")
                        .flashAttr("users", List.of())
                        .flashAttr("currentUser", user.getUsername()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(
            username = "user@mail.com",
            roles = {}
    )
    void testDeleteRole_notAvailableForUser() throws Exception {
        mockMvc.perform(delete("/users/role/remove")
                        .param("id", "2")
                        .param("role", "MODERATOR"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(
            value = "admin@mail.com",
            userDetailsServiceBeanName = "testUserDataService"
    )
    void testRemoveRole_availableForAdmin() throws Exception {
        AppUserDetails user = testDataUtils.getAdmin();
        mockMvc.perform(delete("/service/users/role/remove")
                        .with(csrf())
                        .with(user(user))
                        .param("id", testModerator.getId().toString())
                        .param("role", "MODERATOR"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testAddRole_availableForAdmin() throws Exception {
        AppUserDetails user = testDataUtils.getAdmin();

        mockMvc.perform(put("/service/users/role/add")
                        .with(user(user))
                        .with(csrf())
                        .param("id", testModerator.getId().toString())
                        .param("role", "ROLE_ADMIN"))
                .andExpect(status().is3xxRedirection());
    }
}
