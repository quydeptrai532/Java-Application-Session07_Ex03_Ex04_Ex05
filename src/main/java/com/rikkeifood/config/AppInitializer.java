package com.rikkeifood.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // CẤU HÌNH UPLOAD FILE THEO YÊU CẦU BÀI 3, 4, 5
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // Tham số: (Location, MaxFileSize, MaxRequestSize, FileSizeThreshold)
        // Set maxFileSize = 10MB, maxRequestSize = 15MB (Theo Bài 5)
        long maxFileSize = 10 * 1024 * 1024;
        long maxRequestSize = 15 * 1024 * 1024;
        int fileSizeThreshold = 0; // Luôn ghi ra đĩa

        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                "C:/RikkeiFood_Temp", // Thư mục tạm
                maxFileSize,
                maxRequestSize,
                fileSizeThreshold
        );
        registration.setMultipartConfig(multipartConfigElement);
    }
}