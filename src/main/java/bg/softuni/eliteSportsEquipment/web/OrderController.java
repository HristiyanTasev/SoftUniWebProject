package bg.softuni.eliteSportsEquipment.web;

import bg.softuni.eliteSportsEquipment.model.dto.order.UserOrderDTO;
import bg.softuni.eliteSportsEquipment.service.order.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OrderController {

    //TODO: rework order controller into a RestController :)

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/users/orders/create")
    public String createOrder(Principal principal) {

        this.orderService.createOrderAndClearCart(principal.getName());

        return "redirect:/users/profile";
    }

    @GetMapping("/users/orders/{id}")
    public String userOrder(@PathVariable(name = "id") Long orderId, Model model, Principal principal) {

        UserOrderDTO order = this.orderService.getOrder(orderId, principal.getName());
        model.addAttribute("order", order);

        return "order-details";
    }
}
