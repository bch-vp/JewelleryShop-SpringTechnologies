package by.epam.project.model.dao.impl;

import by.epam.project.exception.DaoException;
import by.epam.project.model.connection.ConnectionPool;
import by.epam.project.model.dao.CategoryDao;
import by.epam.project.model.dao.SqlQuery;
import by.epam.project.model.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.project.controller.parameter.Parameter.ID;
import static by.epam.project.controller.parameter.Parameter.NAME;

/**
 * The type Category dao.
 */
public class CategoryDaoImpl implements CategoryDao {
    private static final Logger logger = LogManager.getLogger();

    private static final CategoryDaoImpl instance = new CategoryDaoImpl();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CategoryDaoImpl getInstance() {
        return instance;
    }

    private static final int ONE_UPDATE = 1;

    private CategoryDaoImpl() {
    }

    @Override
    public Category add(Category category) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.ADD_CATEGORY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong(1);
            category.setId(generatedId);
        } catch (SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return category;
    }

    @Override
    public List<Category> findAllCategories() throws DaoException {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_ALL_CATEGORIES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = toCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return categories;
    }

    @Override
    public Optional<Category> findCategoryByName(String name) throws DaoException {
        Optional<Category> categoryOptional = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_CATEGORY_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                categoryOptional = Optional.of(toCategory(resultSet));
            }
        } catch (SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return categoryOptional;
    }

    @Override
    public Optional<Category> findCategoryById(long id) throws DaoException {
        Optional<Category> categoryOptional = Optional.empty();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FIND_CATEGORY_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                categoryOptional = Optional.of(toCategory(resultSet));
            }
        } catch (SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return categoryOptional;
    }

    @Override
    public boolean updateCategoryNameById(long id, String name) throws DaoException {
        boolean isUpdated;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.UPDATE_CATEGORY_NAME_BY_ID)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            isUpdated = statement.executeUpdate() == ONE_UPDATE;
        } catch (SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return isUpdated;
    }

    @Override
    public boolean removeCategoryById(long id) throws DaoException {
        boolean isUpdated;

        Connection connection = ConnectionPool.getInstance().getConnection();
        try (connection;
             PreparedStatement statementUpdating =
                     connection.prepareStatement(SqlQuery.UPDATE_PRODUCT_CATEGORY_BY_ID_TO_OTHERS);
             PreparedStatement statementRemoving = connection.prepareStatement(SqlQuery.REMOVE_CATEGORY_BY_ID)) {
            try {
                connection.setAutoCommit(false);
                statementUpdating.setLong(1, id);
                statementUpdating.executeUpdate();
                statementRemoving.setLong(1, id);
                isUpdated = statementRemoving.executeUpdate() == ONE_UPDATE;
                connection.commit();
            } catch (SQLException exp) {
                logger.error(exp);
                connection.rollback();
                throw new DaoException(exp);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (
                SQLException exp) {
            logger.error(exp);
            throw new DaoException(exp);
        }

        return isUpdated;
    }

    private Category toCategory(ResultSet resultSet) throws DaoException {
        try {
            long id = resultSet.getLong(ID);
            String name = resultSet.getString(NAME);

            Category category = new Category(id, name);
            return category;
        } catch (SQLException exp) {
            throw new DaoException("Error while creating category from resultSet", exp);
        }
    }
}
