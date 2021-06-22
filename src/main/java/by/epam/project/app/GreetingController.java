package by.epam.project.app;

import by.epam.project.demo.auth.UserRrepository;
import by.epam.project.model.entity.User;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class GreetingController {
    private final UserRrepository userRrepository;

    public GreetingController(UserRrepository userRrepository) {
        this.userRrepository = userRrepository;
    }

    @GetMapping("/")
    public void greeting() {
        Optional<User> user= userRrepository.findById(1L);
        System.out.println(userRrepository.findAll());
    }
}