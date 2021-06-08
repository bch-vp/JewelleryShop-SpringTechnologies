package by.epam.project.model.dao;

import by.epam.project.exception.DaoException;
import by.epam.project.model.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * The interface Category dao.
 */
public interface CategoryDao {
    /**
     * Add category.
     *
     * @param category the category
     * @return the category
     * @throws DaoException the dao exception
     */
    Category add(Category category) throws DaoException;

    /**
     * Find all categories list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Category> findAllCategories() throws DaoException;

    /**
     * Find category by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Category> findCategoryByName(String name) throws DaoException;

    /**
     * Find category by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Category> findCategoryById(long id) throws DaoException;

    /**
     * Update category name by id boolean.
     *
     * @param id   the id
     * @param name the name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateCategoryNameById(long id, String name) throws DaoException;

    /**
     * Remove category by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeCategoryById(long id) throws DaoException;
}
