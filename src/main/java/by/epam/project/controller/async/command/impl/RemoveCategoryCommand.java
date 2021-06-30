package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.CategoryService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ID;

/**
 * The type Remove category command.
 */
public class RemoveCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private CategoryService categoryService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        try {
            Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());
            String idCategoryString = (String) requestParameters.get(ID);

            ajaxData = categoryService.removeCategory(idCategoryString);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during removing category");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
