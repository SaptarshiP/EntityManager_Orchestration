package com.psja.CheckOrchestration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrderRequest {

	@JsonProperty("order_item_name")
	private String orderItemName;
	@JsonProperty("order_price")
	private String orderPrice;
	@JsonProperty("order_item_type")
	private String orderItemType;
	
	public String getOrderItemName() {
		return this.orderItemName;
	}
	public void setOrderItemName( String orderItemName ) {
		this.orderItemName = orderItemName;
	}
	
	public String getOrderPrice() {
		return this.orderPrice;
	}
	public void setOrderPrice( String orderPrice ) {
		this.orderPrice = orderPrice;
	}
	
	public String getOrderItemType() {
		return this.orderItemType;
	}
	public void setOrderItemType( String orderItemType ) {
		this.orderItemType = orderItemType;
	}
}
