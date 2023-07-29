package com.psja.CheckOrchestration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequestDTO {

	@JsonProperty("payment_type")
	private String paymentType;
	@JsonProperty("payment_amount")
	private String paymentAmount;
	@JsonProperty("transaction_id")
	private String transactionId;
	
	public String getPaymentType() {
		return this.paymentType;
	}
	public void setPaymentType( String paymentType ) {
		this.paymentAmount = paymentType;
	}
	
	public String getPaymentAmount() {
		return this.paymentAmount;
	}
	public void setPaymentAmount( String paymentAmount ) {
		this.paymentAmount = paymentAmount;
	}
	
	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	public String getTransactionId() {
		return this.transactionId;
	}
	
}
