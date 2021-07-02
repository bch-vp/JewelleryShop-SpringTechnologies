package by.epam.project.controller.sync;

import by.epam.project.controller.sync.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.project.controller.parameter.Parameter.COMMAND;
import static by.epam.project.controller.parameter.Parameter.IS_DEV_MODE;

/**
 * The type Controller.
 */
@Controller
@RequestMapping("/do")
public class SyncController {
    private static final Logger logger = LogManager.getLogger();

    private final ApplicationContext applicationContext;

    public SyncController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostMapping
    protected String doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return processRequest(request, response);
    }

    @GetMapping
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        return processRequest(request, response);
    }

    private String processRequest(HttpServletRequest request, HttpServletResponse response) {
        boolean isDevMode = Boolean.parseBoolean(System.getenv(IS_DEV_MODE));

        HttpSession session = request.getSession();
        session.setAttribute(IS_DEV_MODE, isDevMode);

        String commandName = request.getParameter(COMMAND);
        Command command = (Command) applicationContext.getBean(request.getParameter(COMMAND));
        Router router = command.execute(request);

        return router.getCurrentPage();
    }
}

