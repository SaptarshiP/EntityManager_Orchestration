package com.psja.CheckOrchestration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psja.CheckOrchestration.repo.BaseTransactionParentRepo;

import org.springframework.context.annotation.Bean;

@Configuration
public class ApplicaationConfig {

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	//@Bean
	ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	
	//@Bean
	//BaseTransactionParentRepo getBaseTransactionParentRepo() {
		//return new BaseTransactionParentRepo(null, null);
	//}
}

