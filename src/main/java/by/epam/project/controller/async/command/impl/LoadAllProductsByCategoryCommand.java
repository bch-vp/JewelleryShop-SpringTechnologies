package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import by.epam.project.service.ProductService;
import by.epam.project.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.NAME;
import static by.epam.project.controller.parameter.Parameter.SHOPPING_CART;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Load all products by category command.
 */
@Component("load_all_products_by_category")
public class LoadAllProductsByCategoryCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ProductService productService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        HttpSession session = request.getSession();
        List<Product> shoppingCart = (ArrayList<Product>) session.getAttribute(SHOPPING_CART);

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());
        String categoryName = (String) requestParameters.get(NAME);

        User user = (User) session.getAttribute(USER);
        User.Role userRole = User.Role.GUEST;
        if (user != null) {
            userRole = user.getRole();
        }

        ajaxData = productService.findAllProductsByCategory(userRole, categoryName, shoppingCart);

        return ajaxData;
    }
}
