package com.psja.CheckOrchestration.service;

import com.psja.CheckOrchestration.request.CreateOrderRequest;

public interface OrderService {

	
	public void createOrder( CreateOrderRequest createOrderRequest )throws Exception;
}
