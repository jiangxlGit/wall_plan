package com.jloved.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jloved.example.kafka", "com.jloved.strive.common.*"})
public class KafkaDemoApp2 {
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApp2.class, args);
    }
    
}