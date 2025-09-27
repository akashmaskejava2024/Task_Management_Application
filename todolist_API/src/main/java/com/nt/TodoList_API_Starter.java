package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.nt.entity")
@EnableJpaRepositories(basePackages = "com.nt.repository")
public class TodoList_API_Starter {
	public static void main(String[] args) {
		SpringApplication.run(TodoList_API_Starter.class, args);
	}
}