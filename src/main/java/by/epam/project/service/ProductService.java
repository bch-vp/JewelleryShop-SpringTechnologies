package by.epam.project.service;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.util.List;

/**
 * The interface Product service.
 */
public interface ProductService {
    AjaxData addProductToShoppingCart(List<Product> shoppingCart, String productName);

    AjaxData findAllProductsByCategory(User.Role userRole, String categoryName,
                                       List<Product> shoppingCart) throws IOException;

    AjaxData removeProductFromShoppingCart(List<Product> shoppingCart, String productName);

    AjaxData loadShoppingCart(List<Product> shoppingCart) throws IOException;

    AjaxData updateProductCategory(String idProductString, String idCategoryString);

    AjaxData updateProductInfo(String idString, String name,
                               String info, String priceString);

    AjaxData updateProductStatus(String idProductString, String idStatusString);

    AjaxData uploadProductImage(String productName, List<FileItem> fileItems, String language) throws IOException;

    AjaxData createProduct(String idCategoryString, String name, String info,
                           String priceString, String language) throws IOException;
}
