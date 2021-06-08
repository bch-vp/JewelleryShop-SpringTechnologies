package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;
import by.epam.project.controller.sync.command.CommandType;
import by.epam.project.util.URLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.project.controller.parameter.Parameter.LANGUAGE;

/**
 * The type Sign out command.
 */
public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();

        String language = (String) session.getAttribute(LANGUAGE);

        session.invalidate();

        session = request.getSession(true);
        session.setAttribute(LANGUAGE, language);

        router.setRedirect();
        String redirectURL = URLUtil.createRedirectURL(request,
                CommandType.PASSING_BY_GUEST.toString().toLowerCase());
        router.setCurrentPage(redirectURL);

        return router;
    }
}
