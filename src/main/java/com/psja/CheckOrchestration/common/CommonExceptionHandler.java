package com.psja.CheckOrchestration.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import com.psja.CheckOrchestration.response.ErrorResponse;

import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler( value = SystemException.class )
	public ResponseEntity<ErrorResponse> handleSystemException( SystemException exp ){
		System.out.println( exp.getMessage() );
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode( exp.getErrorCode() );
		errorResponse.setErrorDescription( exp.getErrorMessage() );
		return ResponseEntity.status(200).contentType( MediaType.APPLICATION_JSON ).body( errorResponse );
	}

}
