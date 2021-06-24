package by.epam.project.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Product;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface OrderService {
    AjaxData createOrder(User user, List<Product> shoppingCart, String orderAddress,
                         String orderComment);
    AjaxData findAllOrders(User user);
    AjaxData updateOrderStatus(String idOrderString, String idStatusString);
}
