package com.psja.CheckOrchestration.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.psja.CheckOrchestration.response.PopResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.json.JSONObject;

@Component
public class RestHandler {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${error.unable.to.connect.external.service.code}")
	private String UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_CODE;
	@Value("${error.unable.to.connect.external.service.message}")
	private String UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_MESSAGE;
	
		
	public String sendData( Object object, String url )throws Exception {
		System.out.println("INSETING INTO sendData");
		String body = null;
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType( MediaType.APPLICATION_JSON );
			httpHeaders.set("Accept", "application/json");
			HttpEntity<Object> httpEntity = new HttpEntity<Object>( object, httpHeaders );
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
			
			body = response.getBody();
		} catch( Exception exp ) {
			throw new SystemException( exp, UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_CODE,
												UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_MESSAGE );
		}
		System.out.println( "EXITING FROM sendData" );
		return body;
	}
	
	public String getData( String url )throws Exception {
		System.out.println( "INSERTING INTO getData" );
		ResponseEntity<String> response = null;
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Accept", "application/json");
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
			response = restTemplate.getForEntity(url, String.class, httpEntity);
			
		}catch( Exception exp ) {
			throw new SystemException( exp, UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_CODE,
													UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_MESSAGE );
		}
		System.out.println( "EXITING FROM GET DATA" );
		return response.getBody();
	}
	
	public String cancelUsingTransactionId( String url ) {
		
		System.out.println("INSERTING INTO cancelUsingTransactionId");
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange( url, HttpMethod.GET, null, String.class );
			
		} catch( Exception exp ) {
			throw new SystemException( exp, UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_CODE,
					UNABLE_TO_CONNECT_EXTERNAL_SERVICE_ERROR_MESSAGE );
		}
				
		System.out.println("EXITING INTO cancelUsingTransactionId" );
		return response.getBody();
	}
}
