package by.epam.project.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    AjaxData createOrder(User user, Set<Product> shoppingCart, String orderAddress,
                         String orderComment);
    AjaxData findAllOrders(User user);

    AjaxData updateStatusById(String idOrderString, String idStatusString);
}
