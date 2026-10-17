package codegym.vn.aspect.exceptionhandler;

import codegym.vn.core.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ModelAndView handler(BusinessException ex) {


        return new ModelAndView();
    }
}
