package com.ankit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class ScProductmgmtApplication {

	public static void main(String[] args) {
		System.out.println("Entered the method");
		ApplicationContext ctx = SpringApplication.run(ScProductmgmtApplication.class, args);
		//Arrays.stream(ctx.getBeanDefinitionNames()).forEach(beans -> System.out.println(beans));
		System.out.println("Exit Method");
	}

	@GetMapping("/")
	public static final String helloWorld() {
		System.err.println("Entered Hello World");
		return "Hello Spring";
	}
}
