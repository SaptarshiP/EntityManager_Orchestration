package com.psja.CheckOrchestration.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDTO {

	@JsonProperty( "transactionId" )
	private String transactionId;
	@JsonProperty("childTransactionDTO")
	private List<ChildTransactionDTO> childTransactionDTO;
		
	public String getTransactionId() {
		return this.transactionId;
	}
	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
	public List<ChildTransactionDTO> getChilTransactionDTOList() {
		return this.childTransactionDTO;
	}
	public void setChildTransactionDTOList( List<ChildTransactionDTO> childTransactionDTO ) {
		this.childTransactionDTO = childTransactionDTO;
	}
		
	public static class ChildTransactionDTO{
		@JsonProperty("id")
		private Integer id;
		@JsonProperty("serviceName")
		private SERVICE_NAME serviceName;
		@JsonProperty("purpose")
		private PURPOSE purpose;
		
		public Integer getId() {
			return this.id;
		}
		public void setId( Integer id ) {
			this.id = id;
		}
		
		public String getServiceName() {
			return this.serviceName.name();
		}
		public void setServiceName( SERVICE_NAME serviceName ) {
			this.serviceName = serviceName;
		}
		
		public String getPurpose() {
			return this.purpose.name();
		}
		public void setPurpose( PURPOSE purpose ) {
			this.purpose = purpose;
		}
	}
	
}
