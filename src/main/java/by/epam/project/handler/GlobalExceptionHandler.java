package by.epam.project.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

import static by.epam.project.controller.parameter.Parameter.COMMAND;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger();

    @ExceptionHandler({UnsatisfiedServletRequestParameterException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUrlException(HttpServletRequest request, Exception ex) {
        return "redirect:/jsp/error404.jsp";
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleThrowableException(HttpServletRequest request, Exception ex) {
        String command = request.getParameter(COMMAND);
        logger.error("Error during " + command, ex);
        return "redirect:/jsp/error500.jsp";
    }
}
