package by.epam.project.controller.sync.command;

import by.epam.project.controller.sync.Router;
import by.epam.project.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     * @throws CommandException the command exception
     */
    Router execute(HttpServletRequest request) throws CommandException;
}
