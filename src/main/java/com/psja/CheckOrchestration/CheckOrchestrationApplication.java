package com.psja.CheckOrchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@PropertySource( value = "classpath:Message.properties" )
public class CheckOrchestrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckOrchestrationApplication.class, args);
	}

}
