package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.entity.User;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.USER;

/**
 * The type Upload profile image command.
 */
@Component("upload_profile_image")
public class UploadProfileImageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserService userService;

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData = new AjaxData();

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        User user = (User) session.getAttribute(USER);
        String login = user.getLogin();

        if (!ServletFileUpload.isMultipartContent(request)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        List<FileItem> fileItems;
        try {
            fileItems = upload.parseRequest(request);
            ajaxData = userService.uploadUserImage(login, fileItems, language);
        } catch (ServiceException | FileUploadException | IOException exp) {
            logger.error("Error during uploading user image");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}