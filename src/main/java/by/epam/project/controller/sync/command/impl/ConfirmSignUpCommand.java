package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;
import by.epam.project.entity.User;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static by.epam.project.controller.parameter.PagePath.NOTIFICATION_SUCCESS;
import static by.epam.project.controller.parameter.Parameter.LOGIN;

/**
 * The type Confirm sign up command.
 */
public class ConfirmSignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private OrderService orderService;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(NOTIFICATION_SUCCESS);

        String login = request.getParameter(LOGIN);

        orderService.updateStatusById(login, User.Status.ACTIVATED.name());

        return router;
    }
}
