package by.epam.project.service.impl;

import by.epam.project.builder.ProductBuilder;
import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Category;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import by.epam.project.repository.CategoryRepository;
import by.epam.project.repository.ProductRepository;
import by.epam.project.service.ProductService;
import by.epam.project.util.ImageUtil;
import by.epam.project.util.JsonUtil;
import by.epam.project.validator.ServiceValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

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
import static by.epam.project.service.impl.ImageCriterion.FILES_COUNT;
import static by.epam.project.service.impl.ImageCriterion.FILE_MAX_SIZE;
import static by.epam.project.service.impl.ImageCriterion.FILE_TYPE;
import static by.epam.project.service.impl.ImageCriterion.FIRST;

/**
 * The type Product service.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public AjaxData findAllProductsByCategory(User.Role userRole, String categoryName,
                                              List<Product> shoppingCart) throws IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(categoryName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        List<Product> products;
        switch (userRole) {
            case GUEST, CLIENT -> {
                products = productRepository.findAllProductsByCategoryToClient(categoryName);

                if (shoppingCart != null) {
                    shoppingCart.forEach(products::remove);
                }
            }
            default -> {
                products = productRepository.findAllProductsByCategoryToAdmin(categoryName);
            }
        }
        String json = JsonUtil.toJson(DATA, products);
        ajaxData.setJson(json);

        return ajaxData;
    }

    @Override
    public AjaxData updateProductCategory(String idProductString, String idCategoryString) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idProductString)
                || !ServiceValidator.isIdCorrect(idCategoryString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idProduct = Long.parseLong(idProductString);
        long idCategory = Long.parseLong(idCategoryString);

        Optional<Category> categoryOptional = categoryRepository.findById(idCategory);
        if (categoryOptional.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        boolean isUpdated = productRepository.updateProductCategory(idCategory, idProduct);
        if (!isUpdated) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProductInfo(String idString, String name, String info, String priceString) {
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

        Optional<Product> productOptional = productRepository.findByName(name);
        if (productOptional.isPresent()
                && productOptional.get().getId() != id) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        boolean isUpdated = productRepository.updateProductInfo(id, name, info, price);
        if (!isUpdated) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateProductStatus(String idProductString, String idStatusString) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idProductString)
                || !ServiceValidator.isIdCorrect(idStatusString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idProduct = Long.parseLong(idProductString);
        int idStatus = Integer.parseInt(idStatusString);

        Product.Status status;
        try {
            status = Product.Status.values()[idStatus];
        } catch (IndexOutOfBoundsException ex) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        boolean isUpdated = productRepository.updateProductStatus(idProduct, idStatus);
        if (!isUpdated) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
        }

        return ajaxData;
    }

    @Override
    public AjaxData addProductToShoppingCart(List<Product> shoppingCart, String productName) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<Product> productOptional = productRepository.findByName(productName);
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

        return ajaxData;
    }

    @Override
    public AjaxData removeProductFromShoppingCart(List<Product> shoppingCart,
                                                  String productName) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<Product> productOptional = productRepository.findByName(productName);
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

        return ajaxData;
    }

    @Override
    public AjaxData loadShoppingCart(List<Product> shoppingCart) throws IOException {
        AjaxData ajaxData = new AjaxData();

            String json = JsonUtil.toJson(DATA, shoppingCart);
            ajaxData.setJson(json);

        return ajaxData;
    }

    @Override
    public AjaxData uploadProductImage(String productName, List<FileItem> fileItems, String language) throws IOException {
        AjaxData ajaxData = new AjaxData();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        if (!ServiceValidator.isNameCorrect(productName)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

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

        Optional<String> URLOptional = productRepository.findImageURLByProductName(productName);
        if (URLOptional.isPresent()) {
            String avatarURL = URLOptional.get();
            ImageUtil.remove(avatarURL);
        }

        String fileURL = ImageUtil.save(file);
        productRepository.updateImageURLByProductName(productName, fileURL);
        JsonUtil.writeJsonToAjaxData(ajaxData, URL, fileURL);

        return ajaxData;
    }

    @Override
    public AjaxData createProduct(String idCategoryString, String name, String info,
                                  String priceString, String language) throws IOException {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idCategoryString)
                || !ServiceValidator.isNameCorrect(name)
                || !ServiceValidator.isInfoCorrect(info)
                || !ServiceValidator.isPriceCorrect(priceString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Optional<Product> productOptional = productRepository.findByName(name);
        if (productOptional.isPresent()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.writeJsonToAjaxData(ajaxData, ERROR, ERROR_NAME_NOT_UNIQUE, language);
            return ajaxData;
        }

        long idCategory = Long.parseLong(idCategoryString);
        BigDecimal price = new BigDecimal(priceString);

        Optional<Category> category = categoryRepository.findById(idCategory);
        if (category.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        Product product = ProductBuilder.builder()
                .setName(name)
                .setInfo(info)
                .setStatus(Product.Status.ACTIVE)
                .setPrice(price)
                .build();
        productRepository.save(product);

        return ajaxData;
    }
}
