package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.parameter.PagePath;
import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;
import by.epam.project.controller.sync.command.CommandType;
import by.epam.project.model.entity.User;
import by.epam.project.util.URLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.project.controller.parameter.Parameter.ENGLISH_LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.RUSSIAN_LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Change language command.
 */
public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = new Router();

        String givenLanguage = request.getParameter(LANGUAGE);
        if (!givenLanguage.equals(ENGLISH_LANGUAGE)
                && !givenLanguage.equals(RUSSIAN_LANGUAGE)) {
            router = new Router(PagePath.ERROR_404);
            return router;
        }


        session.setAttribute(LANGUAGE, givenLanguage);

        router.setRedirect();

        User.Role role = User.Role.GUEST;
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            role = user.getRole();
        }

        switch (role) {
            case CLIENT -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_CLIENT.toString().toLowerCase());
                router.setCurrentPage(redirectURL);
            }
            case ADMIN -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_ADMIN.toString().toLowerCase());
                router.setCurrentPage(redirectURL);
            }
            default -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_GUEST.toString().toLowerCase());
                router.setCurrentPage(redirectURL);
            }
        }
        return router;

    }

}
