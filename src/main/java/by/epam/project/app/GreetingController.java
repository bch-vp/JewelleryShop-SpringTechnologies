//package by.epam.project.app;
//
//import by.epam.project.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//public class GreetingController {
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//
//    @GetMapping(value = "/", params = "d=d")
//    public void greeting() throws Exception {
//        throw new IOException();
//    }
//}