package com.rikkeifood.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleFileSizeException(IllegalStateException ex) {
        ModelAndView mav = new ModelAndView("error-page"); // Trỏ về trang báo lỗi

        // Kiểm tra xem lỗi có phải do vượt quá kích thước file không
        if (ex.getMessage() != null && ex.getMessage().contains("exceeds maximum size")) {
            mav.addObject("errorMessage", "Lỗi: File tải lên quá lớn! Vui lòng chọn file dưới 10MB.");
        } else {
            mav.addObject("errorMessage", "Đã xảy ra lỗi hệ thống: " + ex.getMessage());
        }
        return mav;
    }
}