package by.epam.project.service.imprepo;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.entity.Order;
import by.epam.project.entity.Product;
import by.epam.project.service.OrderService;
import by.epam.project.validator.ServiceValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService{


    @Override
    public AjaxData createOrder(User user, List<Product> shoppingCart, String orderAddress,
                                String orderComment) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isInfoCorrect(orderComment)
                || !ServiceValidator.isAddressCorrect(orderAddress)
                || shoppingCart.isEmpty()) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        BigDecimal totalPrice = shoppingCart.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order(orderComment, orderAddress, new Date(new Date().getTime()), totalPrice, Order.Status.NOT_CONFIRMED);
        try {
            userDao.createOrder(user, order, shoppingCart);
            shoppingCart.clear();
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData findAllOrders(User user) {
        AjaxData ajaxData = new AjaxData();

        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (user.getRole() == User.Role.CLIENT) {
                List<Order> orders = userDao.findAllOrdersToClient(user);

                ArrayNode arrayNodeOrders = objectMapper.valueToTree(orders);
                int size = orders.size();
                for (int i = 0; i < size; i++) {
                    JsonNode orderNode = arrayNodeOrders.path(i);

                    List<Product> products = productDao.findAllOrderProducts(orders.get(i));
                    ArrayNode arrayNodeProducts = objectMapper.valueToTree(products);

                    ((ObjectNode) orderNode).putArray(PRODUCTS).addAll(arrayNodeProducts);
                }
                json = arrayNodeOrders.toString();
            } else {
                List<Order> orders = userDao.findAllOrdersToAdmin();

                ArrayNode arrayNodeOrders = objectMapper.valueToTree(orders);
                int size = orders.size();
                for (int i = 0; i < size; i++) {
                    JsonNode orderNode = arrayNodeOrders.path(i);

                    Optional<User> userOrderOptional = userDao.findUserByOrderId(orders.get(i).getId());
                    User userOrder = userOrderOptional.get();

                    ((ObjectNode) orderNode).put(LOGIN, userOrder.getLogin());
                    ((ObjectNode) orderNode).put(TELEPHONE_NUMBER, userOrder.getTelephoneNumber());
                    ((ObjectNode) orderNode).put(EMAIL, userOrder.getEmail());

                    List<Product> products = productDao.findAllOrderProducts(orders.get(i));
                    ArrayNode arrayNodeProducts = objectMapper.valueToTree(products);

                    ((ObjectNode) orderNode).putArray(PRODUCTS).addAll(arrayNodeProducts);
                }
                json = arrayNodeOrders.toString();
            }
            ajaxData.setJson(json);
        } catch (DaoException exp) {
            throw new ServiceException(exp);
        }

        return ajaxData;
    }

    @Override
    public AjaxData updateOrderStatus(String idOrderString, String idStatusString) {
        AjaxData ajaxData = new AjaxData();

        if (!ServiceValidator.isIdCorrect(idOrderString)
                || !ServiceValidator.isIdCorrect(idStatusString)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        long idOrder = Long.parseLong(idOrderString);
        int idStatus = Integer.parseInt(idStatusString);

        String status;
        try {
            status = User.Status.values()[idStatus].name();
        } catch (IndexOutOfBoundsException ex) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
            return ajaxData;
        }

        boolean isUpdated = userDao.updateOrderStatusById(idOrder, idStatus);
        if (!isUpdated) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_NOT_FOUND);
        }

        return ajaxData;
    }
}
