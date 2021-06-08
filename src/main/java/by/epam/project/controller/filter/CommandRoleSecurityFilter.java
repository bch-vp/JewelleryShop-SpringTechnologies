package by.epam.project.controller.filter;

import by.epam.project.controller.async.command.CommandType;
import by.epam.project.controller.filter.typerole.RolePermission;
import by.epam.project.controller.parameter.PagePath;
import by.epam.project.model.entity.User;

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
import java.util.Arrays;
import java.util.Set;

import static by.epam.project.controller.parameter.Parameter.COMMAND;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Command role security filter.
 */
public class CommandRoleSecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String commandName = request.getParameter(COMMAND);
        if (commandName == null) {
            request.getRequestDispatcher(PagePath.ERROR_404).forward(servletRequest, servletResponse);
            return;
        }

        User.Role role = User.Role.GUEST;
        Set<String> commandsByRole;

        User user = (User) session.getAttribute(USER);
        if (user != null) {
            role = user.getRole();
        }
        switch (role) {
            case CLIENT -> {
                commandsByRole = RolePermission.CLIENT.getCommands();
            }
            case ADMIN -> {
                commandsByRole = RolePermission.ADMIN.getCommands();
            }
            default -> {
                commandsByRole = RolePermission.GUEST.getCommands();
            }
        }

        boolean isContain = Arrays.stream(CommandType.values())
                .anyMatch(commandType -> commandName.equalsIgnoreCase(commandType.toString()));
        if (isContain && !commandsByRole.contains(commandName.toUpperCase())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (!commandsByRole.contains(commandName.toUpperCase())) {
            request.getRequestDispatcher(PagePath.ERROR_404).forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
