package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.User;
import by.epam.project.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Load all orders command.
 */
@Component("load_all_orders")
public class LoadAllOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private OrderService orderService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        ajaxData = orderService.findAllOrders(user);

        return ajaxData;
    }
}