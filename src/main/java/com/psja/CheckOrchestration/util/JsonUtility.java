package com.psja.CheckOrchestration.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtility {

	static JsonUtility instance;
	
	//@Autowired
	private ObjectMapper objectMapper;
	
	public ObjectMapper staticObjectMapper;
	@PostConstruct
	public void getObjectMapper() {
		staticObjectMapper = new ObjectMapper();
	}
	
	
	
	public String toJsonString(Object data) throws JsonProcessingException {
		
		
		return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString( data );
		
	}
	
	public Object readJsonString( String jsonData, Object obj ) throws JsonMappingException, JsonProcessingException {
		return staticObjectMapper.readValue( jsonData, obj.getClass() );
	}
	
}
