package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.service.OrderService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ID_ORDER;
import static by.epam.project.controller.parameter.Parameter.ID_STATUS;

/**
 * The type Update order status command.
 */
public class UpdateOrderStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private OrderService orderService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        try {
            Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

            String idOrderString = (String) requestParameters.get(ID_ORDER);
            String idStatusString = (String) requestParameters.get(ID_STATUS);

            ajaxData = orderService.updateStatusById(idOrderString, idStatusString);
        } catch (IOException exp) {
            logger.error("Error during updating order status");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
