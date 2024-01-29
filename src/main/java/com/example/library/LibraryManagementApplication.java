package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LibraryManagementApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

}
