package com.shyun.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Value("${uploadPath}")
	String uploadPath;

	//ResourceHandlerRegistry 정적자원 제공하기위해 사용
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/images/**")
			.addResourceLocations(uploadPath);
	}
	
}
