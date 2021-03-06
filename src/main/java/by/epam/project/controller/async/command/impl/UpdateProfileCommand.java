package by.epam.project.controller.async.command.impl;

import by.epam.project.builder.UserBuilder;
import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.User;
import by.epam.project.service.UserService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@Component("update_profile")
public class UpdateProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String language = (String) session.getAttribute(LANGUAGE);

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

        User newUser = UserBuilder.builder()
                .setId(user.getId())
                .setLogin(newLogin)
                .setFirstName(newFirstName)
                .setLastName(newLastName)
                .setTelephoneNumber(newTelephoneNumber)
                .setEmail(newEmail)
                .setRole(user.getRole())
                .setStatus(user.getStatus())
                .build();

        session.setAttribute(USER, newUser);

        return ajaxData;
    }
}
