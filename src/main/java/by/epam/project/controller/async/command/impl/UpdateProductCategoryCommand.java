package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.service.ProductService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ID_CATEGORY;
import static by.epam.project.controller.parameter.Parameter.ID_PRODUCT;

/**
 * The type Update product category command.
 */
@Component("update_product_category")
public class UpdateProductCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ProductService productService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

        String idProductString = (String) requestParameters.get(ID_PRODUCT);
        String idCategoryString = (String) requestParameters.get(ID_CATEGORY);

        ajaxData = productService.updateProductCategory(idProductString, idCategoryString);

        return ajaxData;
    }
}
