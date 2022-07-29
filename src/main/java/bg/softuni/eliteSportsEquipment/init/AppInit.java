package bg.softuni.eliteSportsEquipment.init;

import bg.softuni.eliteSportsEquipment.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final BeltService beltService;
    private final SleeveService sleeveService;
    private final StrapService strapService;
    private final CartService cartService;

    public AppInit(UserService userService, BeltService beltService,
                   SleeveService sleeveService, StrapService strapService,
                   CartService cartService) {
        this.userService = userService;
        this.beltService = beltService;
        this.sleeveService = sleeveService;
        this.strapService = strapService;
        this.cartService = cartService;
    }

    @Override
    public void run(String... args) {
        this.userService.init();
        this.beltService.init();
        this.sleeveService.init();
        this.strapService.init();
        this.cartService.init();
    }
}
