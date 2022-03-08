package com.revature.foundation.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// When using annotations for configuration you need a condif class
@Configuration
// we need to tell spring where to look for Spring beans (components)
@ComponentScan("com.revature")
public class AppConfig {
    
}
