package by.epam.project.controller.sync;

import by.epam.project.controller.sync.command.Command;
import by.epam.project.controller.sync.command.CommandProvider;
import by.epam.project.exception.CommandException;
import by.epam.project.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
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

        String currentPage = router.getCurrentPage();

        if (router.getCurrentType().equals(Router.Type.FORWARD)) {
            request.getRequestDispatcher(currentPage).forward(request, response);
        } else {
            response.sendRedirect(currentPage);
        }
    }
}

