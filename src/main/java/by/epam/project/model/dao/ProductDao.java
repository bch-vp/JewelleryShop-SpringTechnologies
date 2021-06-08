package by.epam.project.model.dao;

import by.epam.project.exception.DaoException;
import by.epam.project.model.entity.Order;
import by.epam.project.model.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * The interface Product dao.
 */
public interface ProductDao {
    /**
     * Add boolean.
     *
     * @param product    the product
     * @param idCategory the id category
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(Product product, long idCategory) throws DaoException;

    /**
     * Find image url by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findImageURLByName(String name) throws DaoException;

    /**
     * Update image url by name boolean.
     *
     * @param name    the name
     * @param fileURL the file url
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateImageURLByName(String name, String fileURL) throws DaoException;

    /**
     * Find all products by category to client list.
     *
     * @param category the category
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findAllProductsByCategoryToClient(String category) throws DaoException;

    /**
     * Find all products by category to admin list.
     *
     * @param category the category
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findAllProductsByCategoryToAdmin(String category) throws DaoException;

    /**
     * Find product by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Product> findProductByName(String name) throws DaoException;

    /**
     * Find status by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Product.Status> findStatusById(long id) throws DaoException;

    /**
     * Update product info boolean.
     *
     * @param product the product
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateProductInfo(Product product) throws DaoException;

    /**
     * Update product category boolean.
     *
     * @param idProduct  the id product
     * @param idCategory the id category
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateProductCategory(long idProduct, long idCategory) throws DaoException;

    /**
     * Update product status boolean.
     *
     * @param idProduct the id product
     * @param idStatus  the id status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateProductStatus(long idProduct, long idStatus) throws DaoException;

    /**
     * Find product by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Product> findProductById(long id) throws DaoException;

    /**
     * Find all order products list.
     *
     * @param order the order
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findAllOrderProducts(Order order) throws DaoException;
}
