package andrey019.controller;

import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionController {

    @Autowired
    private LogService logService;


    @ExceptionHandler(CookieTheftException.class)
    public String CookieTheftException(Exception ex, HttpServletRequest request) {
        logService.exception("ip = " + request.getRemoteAddr() + ", exception = " + ex.getMessage());
        return "forward:/";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlePageNotFoundException(Exception ex, HttpServletRequest request) {
        logService.exception("ip = " + request.getRemoteAddr() + ", requestUri = " + request.getRequestURI() + ", exception = " + ex.getMessage());
        return "404";
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, HttpServletRequest request) {
        logService.exception("ip = " + request.getRemoteAddr() + ", exception = " + ex.getMessage());
        ex.printStackTrace();
        return "internal_error";
    }
}
