package by.epam.project.model.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

/**
 * The interface Product service.
 */
public interface ProductService {
    /**
     * Add product to shopping cart ajax data.
     *
     * @param shoppingCart the shopping cart
     * @param productName  the product name
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData addProductToShoppingCart(List<Product> shoppingCart, String productName) throws ServiceException;

    /**
     * Find all products by category ajax data.
     *
     * @param userRole     the user role
     * @param categoryName the category name
     * @param shoppingCart the shopping cart
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData findAllProductsByCategory(User.Role userRole, String categoryName,
                                       List<Product> shoppingCart) throws ServiceException;

    /**
     * Remove product from shopping cart ajax data.
     *
     * @param shoppingCart the shopping cart
     * @param productName  the product name
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData removeProductFromShoppingCart(List<Product> shoppingCart, String productName) throws ServiceException;

    /**
     * Load shopping cart ajax data.
     *
     * @param shoppingCart the shopping cart
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData loadShoppingCart(List<Product> shoppingCart) throws ServiceException;

    /**
     * Update product category ajax data.
     *
     * @param idProductString  the id product string
     * @param idCategoryString the id category string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateProductCategory(String idProductString, String idCategoryString) throws ServiceException;

    /**
     * Update product info ajax data.
     *
     * @param idString    the id string
     * @param name        the name
     * @param info        the info
     * @param priceString the price string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateProductInfo(String idString, String name,
                               String info, String priceString) throws ServiceException;

    /**
     * Update product status ajax data.
     *
     * @param idProductString the id product string
     * @param idStatusString  the id status string
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData updateProductStatus(String idProductString, String idStatusString) throws ServiceException;

    /**
     * Upload product image ajax data.
     *
     * @param productName the product name
     * @param fileItems   the file items
     * @param language    the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData uploadProductImage(String productName, List<FileItem> fileItems, String language) throws ServiceException;

    /**
     * Create product ajax data.
     *
     * @param idCategoryString the id category string
     * @param name             the name
     * @param info             the info
     * @param priceString      the price string
     * @param language         the language
     * @return the ajax data
     * @throws ServiceException the service exception
     */
    AjaxData createProduct(String idCategoryString, String name, String info,
                           String priceString, String language) throws ServiceException;
}
