package com.revature.foundation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // implies both @Configuration and @ComponentScan com.revature.foundation.services
//@ComponentScan("com.revature.foundation.services")
public class Driver {

    public static void main(String[] args) {

        SpringApplication.run(Driver.class);
    }

}