package by.epam.project.controller.filter;

import by.epam.project.controller.sync.command.CommandType;
import by.epam.project.model.entity.User;
import by.epam.project.util.URLUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static by.epam.project.controller.parameter.Parameter.COMMAND;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Empty command role security filter.
 */
public class EmptyCommandRoleSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String commandName = request.getParameter(COMMAND);
        if (commandName != null) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        User.Role role = User.Role.GUEST;

        User user = (User) session.getAttribute(USER);
        if (user != null) {
            role = user.getRole();
        }
        switch (role) {
            case CLIENT -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_CLIENT.toString().toLowerCase());
                response.sendRedirect(redirectURL);
            }
            case ADMIN -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_ADMIN.toString().toLowerCase());
                response.sendRedirect(redirectURL);
            }
            default -> {
                String redirectURL = URLUtil.createRedirectURL(request,
                        CommandType.PASSING_BY_GUEST.toString().toLowerCase());
                response.sendRedirect(redirectURL);
            }
        }
    }
}
