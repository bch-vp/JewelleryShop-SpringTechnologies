package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
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
@Component("sign_up")
public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

        String login = (String) requestParameters.get(LOGIN);
        String password = (String) requestParameters.get(PASSWORD);
        String firstName = (String) requestParameters.get(FIRST_NAME);
        String lastName = (String) requestParameters.get(LAST_NAME);
        String telephoneNumber = (String) requestParameters.get(TELEPHONE_NUMBER);
        String email = (String) requestParameters.get(EMAIL);

        String URL = request.getRequestURL().toString();
        String URI = request.getRequestURI();
        String confirmationLink = URL.replace(URI, EMPTY_STRING);

        ajaxData = userService.signUp(login, password, firstName, lastName, telephoneNumber,
                email, confirmationLink, language);

        return ajaxData;
    }
}