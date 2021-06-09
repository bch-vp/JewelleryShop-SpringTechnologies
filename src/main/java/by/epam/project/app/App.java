package by.epam.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "by.epam.project")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
