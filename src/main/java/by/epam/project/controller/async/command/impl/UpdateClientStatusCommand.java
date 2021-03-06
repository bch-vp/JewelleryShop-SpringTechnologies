package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.service.UserService;
import by.epam.project.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static by.epam.project.controller.parameter.Parameter.ID_STATUS;
import static by.epam.project.controller.parameter.Parameter.ID_USER;

/**
 * The type Update client status command.
 */
@Component("update_client_status")
public class UpdateClientStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AjaxData ajaxData;

        Map<String, Object> requestParameters = JsonUtil.toMap(request.getInputStream());

        String idUserString = (String) requestParameters.get(ID_USER);
        String idStatusString = (String) requestParameters.get(ID_STATUS);

        ajaxData = userService.updateClientStatus(idUserString, idStatusString);

        return ajaxData;
    }
}

