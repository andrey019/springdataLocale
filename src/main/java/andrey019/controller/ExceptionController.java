package andrey019.controller;

import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(CookieTheftException.class)
//    public String CookieTheftException(Exception ex) {
//        return "forward:/";
//    }

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public String handlePageNotFoundException(Exception ex) {
//        return "404";
//    }

//    @ExceptionHandler(Exception.class)
//    public String handleAllExceptions(Exception ex) {
//        return "internal_error";
//    }
}
