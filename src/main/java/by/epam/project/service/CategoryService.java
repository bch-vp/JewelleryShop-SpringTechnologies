package by.epam.project.service;

import by.epam.project.controller.async.AjaxData;

import java.io.IOException;

public interface CategoryService {
    AjaxData createCategory(String nameCategory, String language) throws IOException;
    AjaxData findAllCategories() throws IOException;
    AjaxData removeCategory(String idCategoryString);
    AjaxData updateCategoryName(String idCategoryString, String newName, String language) throws IOException;
}
