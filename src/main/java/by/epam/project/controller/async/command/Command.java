package by.epam.project.controller.async.command;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute ajax data.
     *
     * @param request  the request
     * @param response the response
     * @return the ajax data
     * @throws CommandException the command exception
     */
    AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
