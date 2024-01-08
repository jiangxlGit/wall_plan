package com.jloved.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jloved.strive.common.*"})
public class KafkaDemoApp3 {
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApp3.class, args);
    }
    
}