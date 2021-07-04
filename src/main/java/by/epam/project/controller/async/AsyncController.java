package by.epam.project.controller.async;

import by.epam.project.controller.async.command.Command;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.project.controller.parameter.Parameter.COMMAND;

/**
 * The type Controller.
 */
@RestController
public class AsyncController {
    private static final Logger logger = LogManager.getLogger();

    private final ApplicationContext applicationContext;

    public AsyncController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("/ajax")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    @PostMapping("/ajax")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Command command = (Command) applicationContext.getBean(request.getParameter(COMMAND));
        AjaxData ajaxData = command.execute(request, response);
        JsonUtil.writeAjaxDataToResponse(response, ajaxData);
    }
}
