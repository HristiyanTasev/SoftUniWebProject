package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.service.email.EmailService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseIT {

    @MockitoBean
    private EmailService emailService;
}
