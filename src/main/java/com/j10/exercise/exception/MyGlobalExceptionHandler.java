package com.j10.exercise.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author: Sylvia
 * @version: 1.0
 * @since: 2025/4/14 9:01
 */
@Slf4j
@ControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler(CustomerException.class)
    public String handleCustomerException(CustomerException ex, Model model) {
        Integer code = ex.getCode();
        String message = ex.getMessage();
        model.addAttribute("code", code);
        model.addAttribute("message", message);
        log.info("MyGlobalExceptionHandler处理CustomerException");
        return "error/5xx";
    }
}
