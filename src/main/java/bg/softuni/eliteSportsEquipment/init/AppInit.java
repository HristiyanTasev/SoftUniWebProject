package bg.softuni.eliteSportsEquipment.init;

import bg.softuni.eliteSportsEquipment.service.order.CartService;
import bg.softuni.eliteSportsEquipment.service.order.OrderService;
import bg.softuni.eliteSportsEquipment.service.product.BeltService;
import bg.softuni.eliteSportsEquipment.service.product.SleeveService;
import bg.softuni.eliteSportsEquipment.service.product.StrapService;
import bg.softuni.eliteSportsEquipment.service.user.FavouriteService;
import bg.softuni.eliteSportsEquipment.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final BeltService beltService;
    private final SleeveService sleeveService;
    private final StrapService strapService;
    private final CartService cartService;
    private final OrderService orderService;
    private final FavouriteService favouriteService;

    public AppInit(UserService userService, BeltService beltService,
                   SleeveService sleeveService, StrapService strapService,
                   CartService cartService, OrderService orderService,
                   FavouriteService favouriteService) {
        this.userService = userService;
        this.beltService = beltService;
        this.sleeveService = sleeveService;
        this.strapService = strapService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.favouriteService = favouriteService;
    }

    @Override
    public void run(String... args) {
        this.userService.init();
        this.beltService.init();
        this.sleeveService.init();
        this.strapService.init();
        this.cartService.init();
        this.orderService.init();
        this.favouriteService.init();
    }
}
