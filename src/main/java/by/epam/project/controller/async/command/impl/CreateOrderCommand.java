package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.OrderService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.epam.project.controller.parameter.Parameter.ADDRESS;
import static by.epam.project.controller.parameter.Parameter.COMMENT;
import static by.epam.project.controller.parameter.Parameter.SHOPPING_CART;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Create order command.
 */
@Component("create_order")
public class CreateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private OrderService orderService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Set<Product> shoppingCart = (HashSet<Product>) session.getAttribute(SHOPPING_CART);

        try {
            Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

            String OrderComment = (String) requestParameters.get(COMMENT);
            String OrderAddress = (String) requestParameters.get(ADDRESS);

            ajaxData = orderService.createOrder(user, shoppingCart, OrderAddress, OrderComment);
        } catch (IOException exp) {
            logger.error("Error during creating order");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
