package by.epam.project.model.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.ServiceException;

/**
 * The interface Category service.
 */
public interface CategoryService {
    /**
     * Create category ajax data.
     *
     * @param nameCategory the name category
     * @param language     the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData createCategory(String nameCategory, String language) throws ServiceException;

    /**
     * Find all categories ajax data.
     *
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData findAllCategories() throws ServiceException;

    /**
     * Remove category ajax data.
     *
     * @param idCategoryString the id category string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData removeCategory(String idCategoryString) throws ServiceException;

    /**
     * Update category name ajax data.
     *
     * @param idCategoryString the id category string
     * @param newName          the new name
     * @param language         the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateCategoryName(String idCategoryString, String newName, String language) throws ServiceException;
}
