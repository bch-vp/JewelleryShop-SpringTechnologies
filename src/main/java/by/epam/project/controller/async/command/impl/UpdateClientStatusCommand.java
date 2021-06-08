package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.service.UserService;
import by.epam.project.model.service.impl.UserServiceImpl;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ID_STATUS;
import static by.epam.project.controller.parameter.Parameter.ID_USER;

/**
 * The type Update client status command.
 */
public class UpdateClientStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData;

        try {
            Map<String, String> requestParameters = JsonUtil.toMap(request.getInputStream());

            String idUserString = requestParameters.get(ID_USER);
            String idStatusString = requestParameters.get(ID_STATUS);

            ajaxData = userService.updateClientStatus(idUserString, idStatusString);
        } catch (ServiceException | IOException exp) {
            logger.error("Error during updating client status");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}

