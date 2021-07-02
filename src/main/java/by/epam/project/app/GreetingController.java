package by.epam.project.app;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.Category;
import by.epam.project.entity.Order;
import by.epam.project.entity.Product;
import by.epam.project.exception.ServiceException;
import by.epam.project.repository.CategoryRepository;
import by.epam.project.repository.OrderRepository;
import by.epam.project.repository.ProductRepository;
import by.epam.project.repository.UserRepository;
import by.epam.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GreetingController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ApplicationContext applicationContext;


    @GetMapping(value = "/", params = "d=d")
    public void greeting() throws IOException, ServiceException {
        throw new IllegalStateException();
//        Command command = (Command) applicationContext.getBean("add_product_to_shopping_cart".toLowerCase());
//        AjaxData aa  = categoryService.findAllCategories();
//        System.out.println(aa);
    }
}