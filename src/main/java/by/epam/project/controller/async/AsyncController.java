package by.epam.project.controller.async;

import by.epam.project.controller.async.command.Command;
import by.epam.project.controller.async.command.CommandProvider;
import by.epam.project.exception.CommandException;
import by.epam.project.model.connection.ConnectionPool;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.project.controller.parameter.Parameter.COMMAND;

/**
 * The type Controller.
 */
@RestController
public class AsyncController {
    private static final Logger logger = LogManager.getLogger();

    @GetMapping("/ajax")
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @PostMapping("/ajax")
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Command command = CommandProvider.provideCommand(request.getParameter(COMMAND));
        try {
            AjaxData ajaxData = command.execute(request, response);
            JsonUtil.writeAjaxDataToResponse(response, ajaxData);
        } catch (CommandException | IOException exp) {
            logger.error(exp);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PreDestroy
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
