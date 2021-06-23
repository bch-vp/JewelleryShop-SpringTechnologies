package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.entity.User;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.EMAIL;
import static by.epam.project.controller.parameter.Parameter.FIRST_NAME;
import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.LAST_NAME;
import static by.epam.project.controller.parameter.Parameter.LOGIN;
import static by.epam.project.controller.parameter.Parameter.TELEPHONE_NUMBER;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Update profile command.
 */
public class UpdateProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String language = (String) session.getAttribute(LANGUAGE);

        try {
            Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

            String newLogin = (String) requestParameters.get(LOGIN);
            String newFirstName = (String) requestParameters.get(FIRST_NAME);
            String newLastName = (String) requestParameters.get(LAST_NAME);
            String newTelephoneNumber = (String) requestParameters.get(TELEPHONE_NUMBER);
            String newEmail = (String) requestParameters.get(EMAIL);

            ajaxData = userService.updateProfile(user, newLogin, newFirstName, newLastName, newTelephoneNumber,
                    newEmail, language);
            if (ajaxData.getStatusHttp() != HttpServletResponse.SC_OK) {
                return ajaxData;
            }

            User newUser = new User(user.getId(), newLogin, newFirstName, newLastName, newTelephoneNumber,
                    newEmail, user.getRole(), user.getStatus());
            session.setAttribute(USER, newUser);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during updating user profile");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
