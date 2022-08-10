package bg.softuni.eliteSportsEquipment.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterPageShown() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }

    @Test
    void testUserRegister() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("email", "test@example.com")
                        .param("firstName", "Test")
                        .param("lastName", "Testov")
                        .param("city", "City")
                        .param("address", "Address")
                        .param("password", "asdasd")
                        .param("confirmPassword", "asdasd")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testUserRegister_WithErrors() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("email", "test.com")
                        .param("firstName", "T")
                        .param("lastName", "T")
                        .param("city", "")
                        .param("address", "")
                        .param("password", "asdasd")
                        .param("confirmPassword", "")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));
    }
}
