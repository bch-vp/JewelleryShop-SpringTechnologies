package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.service.CategoryService;
import by.epam.project.model.service.impl.CategoryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The type Load all categories command.
 */
public class LoadAllCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final CategoryService categoryService = CategoryServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        try {
            ajaxData = categoryService.findAllCategories();
        } catch (ServiceException exp) {
            logger.error("Error during loading all categories");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
