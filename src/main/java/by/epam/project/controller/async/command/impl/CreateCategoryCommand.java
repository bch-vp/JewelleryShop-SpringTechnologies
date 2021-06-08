package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.service.CategoryService;
import by.epam.project.model.service.impl.CategoryServiceImpl;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.NAME;

/**
 * The type Create category command.
 */
public class CreateCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final CategoryService categoryService = CategoryServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);

        try {
            Map<String, String> requestParameters = JsonUtil.toMap(request.getInputStream());
            String nameCategory = requestParameters.get(NAME);

            ajaxData = categoryService.createCategory(nameCategory, language);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during creating new category");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
