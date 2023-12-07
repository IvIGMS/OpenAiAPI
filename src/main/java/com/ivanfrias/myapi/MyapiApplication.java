package com.ivanfrias.myapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyapiApplication.class, args);
	}
}