package by.epam.project.model.service.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.DaoException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.dao.CategoryDao;
import by.epam.project.model.dao.impl.CategoryDaoImpl;
import by.epam.project.model.entity.Category;
import by.epam.project.model.service.CategoryService;
import by.epam.project.util.JsonUtil;
import by.epam.project.validator.ServiceValidator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.epam.project.controller.parameter.ContentKey.ERROR_NAME_NOT_UNIQUE;
import static by.epam.project.controller.parameter.ErrorKey.ERROR;
import static by.epam.project.controller.parameter.Parameter.DATA;
import static by.epam.project.controller.parameter.Parameter.OTHERS;

/**
 * The type Category service.
 */
public class CategoryServiceImpl implements CategoryService {
    private static final CategoryServiceImpl instance = new CategoryServiceImpl();

    private CategoryServiceImpl(){}

    private final CategoryDao categoryDao = CategoryDaoImpl.getInstance();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CategoryServiceImpl getInstance() {
        return instance;
    }

    @Override
    public AjaxData createCategory(String nameCategory, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(nameCategory)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<Category> categoryOptional = categoryDao.findCategoryByName(nameCategory);
            if (categoryOptional.isPresent()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
                return ajaxData;
            }

            Category category = new Category(nameCategory);
            category = categoryDao.add(category);
            String json = JsonUtil.toJson(category);
            ajaxData.setJson(json);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData findAllCategories() throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        try {
            List<Category> categories = categoryDao.findAllCategories();

            String json = JsonUtil.toJson(DATA, categories);
            ajaxData.setJson(json);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData removeCategory(String idCategoryString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            long idCategory = Long.parseLong(idCategoryString);
            Optional<Category> categoryOptional = categoryDao.findCategoryById(idCategory);
            if (categoryOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            Category category = categoryOptional.get();
            if (category.getName().equals(OTHERS)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            categoryDao.removeCategoryById(idCategory);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateCategoryName(String idCategoryString, String newName, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString) ||
                !ServiceValidator.isNameCorrect(newName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idCategory = Long.parseLong(idCategoryString);
        try {
            Optional<Category> categoryOptional = categoryDao.findCategoryById(idCategory);
            if (categoryOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            Category category = categoryOptional.get();
            if (category.getName().equals(OTHERS)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            categoryOptional = categoryDao.findCategoryByName(newName);
            if (categoryOptional.isPresent()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
                return ajaxData;
            }

            categoryDao.updateCategoryNameById(idCategory, newName);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }
}
