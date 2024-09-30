package com.TaleWeaver.FirstBackendPrototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.TaleWeaver.FirstBackendPrototype.repositories")
public class FirstBackendPrototypeApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstBackendPrototypeApplication.class, args);
	}

}
