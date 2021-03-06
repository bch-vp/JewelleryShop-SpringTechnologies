package by.epam.project.controller.async.command;

import by.epam.project.controller.async.AjaxData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     */
    AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
