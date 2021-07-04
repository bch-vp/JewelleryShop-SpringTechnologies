package by.epam.project.controller.sync.command;

import by.epam.project.controller.sync.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {
    Router execute(HttpServletRequest request);
}
