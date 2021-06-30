package by.epam.project.service.impl;

import by.epam.project.builder.CategoryBuilder;
import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Category;
import by.epam.project.exception.ServiceException;
import by.epam.project.repository.CategoryRepository;
import by.epam.project.service.CategoryService;
import by.epam.project.util.JsonUtil;
import by.epam.project.validator.ServiceValidator;
import org.springframework.stereotype.Service;

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
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public AjaxData createCategory(String nameCategory, String language) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(nameCategory)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<Category> categoryOptional = categoryRepository.findByName(nameCategory);
        if (categoryOptional.isPresent()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
            return ajaxData;
        }

        Category category = CategoryBuilder.builder()
                .setName(nameCategory)
                .build();
        category = categoryRepository.save(category);
        String json = JsonUtil.toJson(category);
        ajaxData.setJson(json);

        return ajaxData;
    }

    @Override
    public AjaxData findAllCategories() throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        List<Category> categories = categoryRepository.findAll();

        String json = JsonUtil.toJson(DATA, categories);
        ajaxData.setJson(json);

        return ajaxData;
    }

    @Override
    public AjaxData removeCategory(String idCategoryString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idCategory = Long.parseLong(idCategoryString);
        Optional<Category> categoryOptional = categoryRepository.findById(idCategory);
        if (categoryOptional.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        Category category = categoryOptional.get();
        if (category.getName().equals(OTHERS)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        categoryRepository.deleteById(idCategory);

        return ajaxData;
    }

    @Override
    public AjaxData updateCategoryName(String idCategoryString, String newName, String language) throws ServiceException, IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString) ||
                !ServiceValidator.isNameCorrect(newName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idCategory = Long.parseLong(idCategoryString);

        Optional<Category> categoryOptional = categoryRepository.findById(idCategory);
        if (categoryOptional.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        Category category = categoryOptional.get();
        if (category.getName().equals(OTHERS)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        categoryOptional = categoryRepository.findByName(newName);
        if (categoryOptional.isPresent()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
            return ajaxData;
        }

        categoryRepository.updateNameById(newName, idCategory);

        return ajaxData;
    }
}
