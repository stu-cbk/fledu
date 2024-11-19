package com.example.virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class VirtualApplication {
    public static void main(String[] args){
        SpringApplication.run(VirtualApplication.class, args);
    }
}
