package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.entity.User;
import by.epam.project.model.service.UserService;
import by.epam.project.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.project.controller.parameter.PagePath.NOTIFICATION_SUCCESS;
import static by.epam.project.controller.parameter.Parameter.LOGIN;

/**
 * The type Confirm sign up command.
 */
public class ConfirmSignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(NOTIFICATION_SUCCESS);

        String login = request.getParameter(LOGIN);

        try {
            userService.updateActivationStatusByLogin(login, User.Status.ACTIVATED);
        } catch (ServiceException exp) {
            logger.error("Error during confirming sign up");
            throw new CommandException(exp);
        }

        return router;
    }
}
