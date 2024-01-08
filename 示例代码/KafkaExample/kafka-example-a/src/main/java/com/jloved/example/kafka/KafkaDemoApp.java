package com.jloved.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jloved.example.kafka", "com.jloved.strive.common.*"})
public class KafkaDemoApp {
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApp.class, args);
    }
    
}