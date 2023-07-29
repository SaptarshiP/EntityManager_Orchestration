package com.psja.CheckOrchestration.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.psja.CheckOrchestration.dto.DELIVERY_PARTNER_NAME;
import com.psja.CheckOrchestration.dto.PAYMENT_TYPE;

public class CreateOrderRequest {

	@JsonProperty("order_item_name")
	private String orderItemName;
	@JsonProperty("order_price")
	private String orderPrice;
	@JsonProperty("order_item_type")
	private String orderItemType;
	@JsonProperty("payment_type")
	private PAYMENT_TYPE paymentType;
	@JsonProperty("delivery_partner_name")
	public DELIVERY_PARTNER_NAME deliveryPartnerName;
	
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
	
	public PAYMENT_TYPE getPaymentMode() {
		return this.paymentType;
	}
	public void setPaymentType( PAYMENT_TYPE paymentType ) {
		this.paymentType = paymentType;
	}
	
	public DELIVERY_PARTNER_NAME getDeliveryPartnerName() {
		return this.deliveryPartnerName;
	}
	public void setDeliveryPartnerName( DELIVERY_PARTNER_NAME deliveryPartnerName ) {
		this.deliveryPartnerName = deliveryPartnerName;
	}
}
