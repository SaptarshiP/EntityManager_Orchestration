package com.psja.CheckOrchestration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

	@JsonProperty("error_code")
	private String errorCode;
	@JsonProperty("error_message")
	private String errorDescription;
	
	public void setErrorCode( String errorCode ) {
		this.errorCode = errorCode;
	}
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public void setErrorDescription( String errorDescription ) {
		this.errorDescription = errorDescription;
	}
	public String getErrorDescription() {
		return this.errorDescription;
	}
	
}
