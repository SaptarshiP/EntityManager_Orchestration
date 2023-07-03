package com.psja.CheckOrchestration.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.psja.CheckOrchestration.request.CreateOrderRequest;
import com.psja.CheckOrchestration.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping( value = "/CREATE_ORDER" )
	public ResponseEntity<String> createOrder( @RequestBody CreateOrderRequest createOrderRequest  )throws Exception{
		
		orderService.createOrder(createOrderRequest);
		
		return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body( null );
	}
	
	
}
