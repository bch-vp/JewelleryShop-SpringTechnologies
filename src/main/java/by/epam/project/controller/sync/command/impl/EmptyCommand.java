package by.epam.project.controller.sync.command.impl;

import by.epam.project.controller.parameter.PagePath;
import by.epam.project.controller.sync.Router;
import by.epam.project.controller.sync.command.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Empty command.
 */
public class EmptyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.ERROR_404);
    }
}
