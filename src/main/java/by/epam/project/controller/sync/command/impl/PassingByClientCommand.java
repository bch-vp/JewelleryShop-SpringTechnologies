package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.parameter.PagePath;
import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Passing by client command.
 */
public class PassingByClientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.PASSING_BY_CLIENT);
    }
}
