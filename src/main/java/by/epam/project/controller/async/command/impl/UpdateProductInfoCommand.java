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

import static by.epam.project.controller.parameter.Parameter.ID;
import static by.epam.project.controller.parameter.Parameter.INFO;
import static by.epam.project.controller.parameter.Parameter.NAME;
import static by.epam.project.controller.parameter.Parameter.PRICE;

/**
 * The type Update product info command.
 */
@Component("update_product_info")
public class UpdateProductInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ProductService productService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

        String idString = (String) requestParameters.get(ID);
        String name = (String) requestParameters.get(NAME);
        String info = (String) requestParameters.get(INFO);
        String priceString = (String) requestParameters.get(PRICE);

        ajaxData = productService.updateProductInfo(idString, name, info, priceString);

        return ajaxData;
    }
}
