package com.github.arpit.nexus.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.arpit.nexus.service", "com.github.arpit.nexus.core"})
public class NexusReferenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusReferenceServiceApplication.class, args);
    }

}