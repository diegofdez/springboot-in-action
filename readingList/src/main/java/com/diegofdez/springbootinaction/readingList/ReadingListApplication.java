package com.diegofdez.springbootinaction.readingList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ReadingListApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ReadingListApplication.class, args);
	}
	
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	// Declaration of Login controller. Instead of using the actual controller class
    	registry.addViewController("/login").setViewName("login");
    }
}
