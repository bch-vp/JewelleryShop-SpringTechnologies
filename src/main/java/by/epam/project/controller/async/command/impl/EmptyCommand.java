package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Empty command.
 */
public class EmptyCommand implements Command {
    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) {
        return new AjaxData(HttpServletResponse.SC_NOT_FOUND);
    }
}
