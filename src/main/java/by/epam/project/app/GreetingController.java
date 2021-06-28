package by.epam.project.app;

import by.epam.project.entity.Order;
import by.epam.project.repository.OrderRepository;
import by.epam.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {
    private final UserRepository userRrepository;

    @Autowired
    OrderRepository orderRepository;

    public GreetingController(UserRepository userRrepository) {
        this.userRrepository = userRrepository;
    }

    @GetMapping("/")
    public void greeting() {
        List<Order> aa  = orderRepository.findAll();
        System.out.println(aa);
    }
}