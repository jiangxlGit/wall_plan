package com.jloved.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jloved.strive.common.*"})
public class KafkaDemoApp4 {
    
    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApp4.class, args);
    }
    
}