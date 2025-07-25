package com.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;


@SpringBootApplication
public class BlogAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

}
