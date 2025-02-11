package com.property.tax.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("errorMessage", ex.getMessage());
        redirectAttributes.addFlashAttribute("message", "An unexpected error occurred: " + ex.getMessage());
        return "error";
    }
   
}