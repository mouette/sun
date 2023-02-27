package com.sogeti.sun.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("drools.multithreadEvaluation", "true");
		SpringApplication.run(DemoApplication.class, args);
	}

}
