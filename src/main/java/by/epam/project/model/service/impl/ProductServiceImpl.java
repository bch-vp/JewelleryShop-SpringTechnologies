package by.epam.project.model.service.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.exception.DaoException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.dao.CategoryDao;
import by.epam.project.model.dao.ProductDao;
import by.epam.project.model.dao.impl.CategoryDaoImpl;
import by.epam.project.model.dao.impl.ProductDaoImpl;
import by.epam.project.model.entity.Category;
import by.epam.project.model.entity.Product;
import by.epam.project.model.entity.User;
import by.epam.project.model.service.ProductService;
import by.epam.project.util.ImageUtil;
import by.epam.project.util.JsonUtil;
import by.epam.project.validator.ServiceValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static by.epam.project.controller.parameter.ContentKey.ERROR_NAME_NOT_UNIQUE;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_COUNT_ALLOWED_FILES;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_FORMAT;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_FORM_FIELD;
import static by.epam.project.controller.parameter.ContentKey.ERROR_PROFILE_AVATAR_MAX_SIZE;
import static by.epam.project.controller.parameter.ErrorKey.ERROR;
import static by.epam.project.controller.parameter.Parameter.DATA;
import static by.epam.project.controller.parameter.Parameter.URL;
import static by.epam.project.model.service.impl.ImageCriterion.FILES_COUNT;
import static by.epam.project.model.service.impl.ImageCriterion.FILE_MAX_SIZE;
import static by.epam.project.model.service.impl.ImageCriterion.FILE_TYPE;
import static by.epam.project.model.service.impl.ImageCriterion.FIRST;

/**
 * The type Product service.
 */
public class ProductServiceImpl implements ProductService {
    private static final ProductServiceImpl instance = new ProductServiceImpl();

    private final ProductDao productDao = ProductDaoImpl.getInstance();
    private final CategoryDao categoryDao = CategoryDaoImpl.getInstance();

    private ProductServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ProductServiceImpl getInstance() {
        return instance;
    }

    @Override
    public AjaxData findAllProductsByCategory(User.Role userRole, String categoryName,
                                              List<Product> shoppingCart) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(categoryName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        List<Product> products;
        try {
            switch (userRole) {
                case GUEST, CLIENT -> {
                    products = productDao.findAllProductsByCategoryToClient(categoryName);

                    if (shoppingCart != null) {
                        shoppingCart.forEach(products::remove);
                    }
                }
                default -> {
                    products = productDao.findAllProductsByCategoryToAdmin(categoryName);
                }
            }
            String json = JsonUtil.toJson(DATA, products);
            ajaxData.setJson(json);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProductCategory(String idProductString, String idCategoryString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idProductString)
                || !ServiceValidator.isIdCorrect(idCategoryString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idProduct = Long.parseLong(idProductString);
        long idCategory = Long.parseLong(idCategoryString);

        try {
            Optional<Category> categoryOptional = categoryDao.findCategoryById(idCategory);
            if (categoryOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            boolean isUpdated = productDao.updateProductCategory(idProduct, idCategory);
            if (!isUpdated) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProductInfo(String idString, String name, String info, String priceString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idString)
                || !ServiceValidator.isNameCorrect(name)
                || !ServiceValidator.isInfoCorrect(info)
                || !ServiceValidator.isPriceCorrect(priceString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long id = Long.parseLong(idString);
        BigDecimal price = new BigDecimal(priceString);
        try {
            Optional<Product> productOptional = productDao.findProductByName(name);
            if(productOptional.isPresent()
                    && productOptional.get().getId()!=id){
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            Product product = new Product(id, name, info, price);
            boolean isUpdated = productDao.updateProductInfo(product);
            if (!isUpdated) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProductStatus(String idProductString, String idStatusString) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idProductString)
                || !ServiceValidator.isIdCorrect(idStatusString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idProduct = Long.parseLong(idProductString);
        long idStatus = Long.parseLong(idStatusString);

        try {
            Optional<Product.Status> statusOptional = productDao.findStatusById(idStatus);
            if (statusOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            boolean isUpdated = productDao.updateProductStatus(idProduct, idStatus);
            if (!isUpdated) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData addProductToShoppingCart(List<Product> shoppingCart, String productName) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<Product> productOptional = productDao.findProductByName(productName);
            if (productOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            Product product = productOptional.get();
            if (shoppingCart.contains(product)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            shoppingCart.add(product);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData removeProductFromShoppingCart(List<Product> shoppingCart,
                                                  String productName) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<Product> productOptional = productDao.findProductByName(productName);
            if (productOptional.isEmpty()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
                return ajaxData;
            }

            Product product = productOptional.get();
            if (!shoppingCart.contains(product)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                return ajaxData;
            }

            shoppingCart.remove(product);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData loadShoppingCart(List<Product> shoppingCart) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        try {
            String json = JsonUtil.toJson(DATA, shoppingCart);
            ajaxData.setJson(json);
        } catch (IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData uploadProductImage(String productName, List<FileItem> fileItems, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            if (fileItems.size() != FILES_COUNT) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_COUNT_ALLOWED_FILES, language);
                return ajaxData;
            }

            FileItem file = fileItems.get(FIRST);
            if (file.getSize() > FILE_MAX_SIZE) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_MAX_SIZE, language);
                return ajaxData;
            }

            if (file.isFormField()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_FORM_FIELD, language);
                return ajaxData;
            }

            String contentType = file.getContentType();
            if (!FILE_TYPE.contains(contentType)) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_PROFILE_AVATAR_FORMAT, language);
                return ajaxData;
            }

            Optional<String> URLOptional = productDao.findImageURLByName(productName);
            if (URLOptional.isPresent()) {
                String avatarURL = URLOptional.get();
                ImageUtil.remove(avatarURL);
            }

            String fileURL = ImageUtil.save(file);
            productDao.updateImageURLByName(productName, fileURL);
            JsonUtil.writeJsonToAjaxData(ajaxData, URL, fileURL);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData createProduct(String idCategoryString, String name, String info,
                                  String priceString, String language) throws ServiceException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString)
                || !ServiceValidator.isNameCorrect(name)
                || !ServiceValidator.isInfoCorrect(info)
                || !ServiceValidator.isPriceCorrect(priceString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        try {
            Optional<Product> productOptional = productDao.findProductByName(name);
            if (productOptional.isPresent()) {
                ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
                return ajaxData;
            }

            long idCategory = Long.parseLong(idCategoryString);
            BigDecimal price = new BigDecimal(priceString);

            Product product = new Product(name, info, Product.Status.ACTIVE, price);
            productDao.add(product, idCategory);
        } catch (DaoException | IOException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }
}
