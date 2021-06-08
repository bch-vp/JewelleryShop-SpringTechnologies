package by.epam.project.controller.async.command.impl;

import by.epam.project.controller.async.AjaxData;
import by.epam.project.controller.async.command.Command;
import by.epam.project.exception.CommandException;
import by.epam.project.exception.ServiceException;
import by.epam.project.model.service.ProductService;
import by.epam.project.model.service.impl.ProductServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.epam.project.controller.parameter.Parameter.LANGUAGE;
import static by.epam.project.controller.parameter.Parameter.NAME;

/**
 * The type Upload product image command.
 */
public class UploadProductImageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public AjaxData execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AjaxData ajaxData = new AjaxData();

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LANGUAGE);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        String productName = request.getParameter(NAME);

        if (!ServletFileUpload.isMultipartContent(request)) {
            ajaxData.setStatusHttp(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxData;
        }

        List<FileItem> fileItems;
        try {
            fileItems = upload.parseRequest(request);
            ajaxData = productService.uploadProductImage(productName, fileItems, language);
        } catch (ServiceException | FileUploadException exp) {
            logger.error("Error during uploading product image");
            throw new CommandException(exp);
        }

        return ajaxData;
    }
}
