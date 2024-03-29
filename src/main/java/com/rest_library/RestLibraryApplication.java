package com.rest_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:mysql.properties")
@SpringBootApplication
public class RestLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestLibraryApplication.class, args);
    }

}
