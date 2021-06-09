package by.epam.project.controller.sync;

import by.epam.project.controller.sync.command.Command;
import by.epam.project.controller.sync.command.CommandProvider;
import by.epam.project.exception.CommandException;
import by.epam.project.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.epam.project.controller.parameter.PagePath.ERROR_500;
import static by.epam.project.controller.parameter.Parameter.COMMAND;
import static by.epam.project.controller.parameter.Parameter.IS_DEV_MODE;

/**
 * The type Controller.
 */
@Controller
public class SyncController {
    private static final Logger logger = LogManager.getLogger();

    @PostMapping("/do")
    protected String doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return processRequest(request, response);
    }

    @GetMapping("/do")
    protected String doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return processRequest(request, response);
    }

    private String processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean isDevMode = Boolean.parseBoolean(System.getenv(IS_DEV_MODE));

        HttpSession session = request.getSession();
        session.setAttribute(IS_DEV_MODE, isDevMode);

        String commandName = request.getParameter(COMMAND);
        Command command = CommandProvider.provideCommand(commandName);
        Router router = new Router();
        try {
            router = command.execute(request);
        } catch (CommandException exp) {
            logger.error(exp);
            router.setCurrentPage(ERROR_500);
        }

        return router.getCurrentPage();
    }
}

