package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.service.product.AllProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AllProductsService mockAllproductsService;

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/")
                        .flashAttr("featuredProducts", mockAllproductsService.getFeaturedProducts()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testGetAboutUs() throws Exception {
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"));
    }
}
