package com.psja.CheckOrchestration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentSuccessResponse {

	@JsonProperty("status")
	private String status;
	
	public void setStatus( String status ) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
	
}
