package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Sign in command.
 */
public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData = null;//todo del null
//
//        HttpSession session = request.getSession();
//        String language = (String) session.getAttribute(LANGUAGE);
//
//        try {
//            Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());
//
//            String login = (String) requestParameters.get(LOGIN);
//            String password = (String) requestParameters.get(PASSWORD);
//
//            ajaxData = userService.signIn(login, password, language);
//            if (ajaxData.getStatusHttp() != HttpServletResponse.SC_OK) {
//                return ajaxData;
//            }
//
//            session.setAttribute(USER, ajaxData.getDataSession().get(USER));
//            session.setAttribute(SHOPPING_CART, new HashSet<>());
//        } catch (ServiceException | IOException exp) {
//            logger.error("Error during sign in");
//            throw new CommandException(exp);
//        }

        return ajaxData;
    }
}
