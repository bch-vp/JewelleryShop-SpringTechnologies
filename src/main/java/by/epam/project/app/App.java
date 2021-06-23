package by.epam.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "by.epam.project")
@EnableJpaRepositories("by.epam.project.*")
@EntityScan("by.epam.project.*")
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
