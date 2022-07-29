package bg.softuni.eliteSportsEquipment.init;

import bg.softuni.eliteSportsEquipment.service.BeltService;
import bg.softuni.eliteSportsEquipment.service.SleeveService;
import bg.softuni.eliteSportsEquipment.service.StrapService;
import bg.softuni.eliteSportsEquipment.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final BeltService beltService;
    private final SleeveService sleeveService;
    private final StrapService strapService;

    public AppInit(UserService userService, BeltService beltService, SleeveService sleeveService, StrapService strapService) {
        this.userService = userService;
        this.beltService = beltService;
        this.sleeveService = sleeveService;
        this.strapService = strapService;
    }

    @Override
    public void run(String... args) {
        this.userService.init();
        this.beltService.init();
        this.sleeveService.init();
        this.strapService.init();
    }
}
