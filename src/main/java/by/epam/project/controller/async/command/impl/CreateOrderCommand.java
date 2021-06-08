package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;
import by.epam.project.model.service.UserService;
import by.epam.project.model.service.impl.UserServiceImpl;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ADDRESS;
import static by.epam.project.controller.parameter.Parameter.COMMENT;
import static by.epam.project.controller.parameter.Parameter.SHOPPING_CART;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Create order command.
 */
public class CreateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        List<Product> shoppingCart = (ArrayList<Product>) session.getAttribute(SHOPPING_CART);

        try {
            Map<String, String> requestParameters = JsonUtil.toMap(request.getInputStream());

            String OrderComment = requestParameters.get(COMMENT);
            String OrderAddress = requestParameters.get(ADDRESS);

            ajaxData = userService.createOrder(user, shoppingCart, OrderAddress, OrderComment);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during creating order");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
