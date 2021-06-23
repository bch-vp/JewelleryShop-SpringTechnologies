package by.epam.project.app;

import by.epam.project.repository.UserRepository;
import by.epam.project.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GreetingController {
    private final UserRepository userRrepository;

    public GreetingController(UserRepository userRrepository) {
        this.userRrepository = userRrepository;
    }

    @GetMapping("/")
    public void greeting() {
        Optional<User> user= userRrepository.findById(1L);
        System.out.println(userRrepository.findAll());
    }
}