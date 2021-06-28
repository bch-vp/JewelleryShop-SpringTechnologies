package by.epam.project.app;

import by.epam.project.entity.Category;
import by.epam.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication(scanBasePackages = "by.epam.project")
@EnableJpaRepositories("by.epam.project.*")
@EntityScan("by.epam.project.*")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
