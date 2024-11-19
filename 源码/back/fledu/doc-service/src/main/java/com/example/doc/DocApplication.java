package com.example.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DocApplication {
    public static void main(String[] args){
        SpringApplication.run(DocApplication.class, args);
    }
}
