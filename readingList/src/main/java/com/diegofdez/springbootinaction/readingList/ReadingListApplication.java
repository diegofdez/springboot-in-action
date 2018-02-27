package com.diegofdez.springbootinaction.readingList;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

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
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	argumentResolvers.add(new ReaderHandlerMethodArgumentResolver());
    }
}
