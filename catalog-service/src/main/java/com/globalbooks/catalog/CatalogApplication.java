package com.globalbooks.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CatalogApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }
}
