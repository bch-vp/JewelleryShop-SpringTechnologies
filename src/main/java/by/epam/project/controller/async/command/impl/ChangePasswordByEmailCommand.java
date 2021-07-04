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
import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.LOGIN;
import static by.epam.project.controller.parameter.Parameter.NEW_PASSWORD;
import static by.epam.project.controller.parameter.Parameter.TIME_CREATED;
import static by.epam.project.controller.parameter.Parameter.UNIQUE_KEY;

/**
 * The type Change password by email command.
 */
@Component("change_password_by_email")
public class ChangePasswordByEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);
        String sessionUniqueKey = (String) session.getAttribute(UNIQUE_KEY);
        String timeCreated = (String) session.getAttribute(TIME_CREATED);

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

        String login = (String) requestParameters.get(LOGIN);
        String email = (String) requestParameters.get(EMAIL);
        String newPassword = (String) requestParameters.get(NEW_PASSWORD);
        String requestUniqueKey = (String) requestParameters.get(UNIQUE_KEY);

        ajaxData = userService.changePasswordByEmail(login, newPassword, email, sessionUniqueKey, requestUniqueKey,
                timeCreated, language);

        if (ajaxData.getStatusHttp() == HttpServletResponse.SC_UNAUTHORIZED) {
            String uniqueKey = (String) ajaxData.getDataSession().get(UNIQUE_KEY);
            session.setAttribute(TIME_CREATED, String.valueOf(System.currentTimeMillis()));
            session.setAttribute(UNIQUE_KEY, uniqueKey);
        } else if (ajaxData.getStatusHttp() == HttpServletResponse.SC_REQUEST_TIMEOUT) {
            session.removeAttribute(UNIQUE_KEY);
        }

        return ajaxData;


    }
}
