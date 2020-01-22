package com.vkakarla.springboot.exceptionhandling.junits.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@ComponentScan(value = "com.vkakarla.springboot.exceptionhandling.junits.*")
public class ExceptionHandlingAndJunitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExceptionHandlingAndJunitsApplication.class, args);
	}
	
	@Bean
	public Docket saggerapi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.vkakarla.springboot.exceptionhandling.junits.controller")).build();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/person-error-messages");
		return messageSource;
	}
	
}
