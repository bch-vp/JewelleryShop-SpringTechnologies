package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The type Load all categories command.
 */
@Component("load_all_categories")
public class LoadAllCategoriesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoryService categoryService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        ajaxData = categoryService.findAllCategories();

        return ajaxData;
    }
}
