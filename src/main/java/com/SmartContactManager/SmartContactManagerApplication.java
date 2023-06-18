package com.SmartContactManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;

@SpringBootApplication
@ComponentScan({"com/SmartContactManager/dao","com/SmartContactManager/Controller","com/SmartContactManager/Config"})
@EntityScan("com/SmartContactManager/entities")
public class SmartContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
	}

}
