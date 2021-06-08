package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.service.UserService;
import by.epam.project.model.service.impl.UserServiceImpl;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.EMAIL;
import static by.epam.project.controller.parameter.Parameter.EMPTY_STRING;
import static by.epam.project.controller.parameter.Parameter.FIRST_NAME;
import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.LAST_NAME;
import static by.epam.project.controller.parameter.Parameter.LOGIN;
import static by.epam.project.controller.parameter.Parameter.PASSWORD;
import static by.epam.project.controller.parameter.Parameter.TELEPHONE_NUMBER;

/**
 * The type Sign up command.
 */
public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);

        try {
            Map<String, String> requestParameters = JsonUtil.toMap(request.getInputStream());

            String login = requestParameters.get(LOGIN);
            String password = requestParameters.get(PASSWORD);
            String firstName = requestParameters.get(FIRST_NAME);
            String lastName = requestParameters.get(LAST_NAME);
            String telephoneNumber = requestParameters.get(TELEPHONE_NUMBER);
            String email = requestParameters.get(EMAIL);

            String URL = request.getRequestURL().toString();
            String URI = request.getRequestURI();
            String confirmationLink = URL.replace(URI, EMPTY_STRING);

            ajaxData = userService.signUp(login, password, firstName, lastName, telephoneNumber,
                    email, confirmationLink, language);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during sign up");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}