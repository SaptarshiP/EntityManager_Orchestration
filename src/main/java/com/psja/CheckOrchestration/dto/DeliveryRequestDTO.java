package com.psja.CheckOrchestration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryRequestDTO {

	@JsonProperty("transaction_id")
	private String transactionId;
	@JsonProperty("goods_type")
	private String goodsType;
	@JsonProperty("delivery_partner_name")
	private String deliveryPartnerName;
	
	public String getTransactionId() {
		return this.transactionId;
	}
	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	
	public String getGoodsType() {
		return this.goodsType;
	}
	public void setGoodsType( String goodsType ) {
		this.goodsType = goodsType;
	}
	
	public String getDeliveryPartnerName() {
		return this.deliveryPartnerName;
	}
	public void setDeliveryPartnerName( String deliveryPartnerName ) {
		this.deliveryPartnerName = deliveryPartnerName;
	}
}
