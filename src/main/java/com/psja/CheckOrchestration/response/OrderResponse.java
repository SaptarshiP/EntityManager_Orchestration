package com.psja.CheckOrchestration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResponse {

	@JsonProperty( value = "status" )
	private String status;
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus( String status ) {
		this.status = status;
	}
	
}
