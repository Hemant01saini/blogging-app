package com.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableCaching
public class BloggingAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApplication.class, args);
	}
}


