package org.example.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = "org.example.application")
@Configuration
@EnableWebMvc
public class WebContextConfig implements WebMvcConfigurer {

}
