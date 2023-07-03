package com.psja.CheckOrchestration.service.impl;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.psja.CheckOrchestration.common.RestHandler;
import com.psja.CheckOrchestration.dto.PURPOSE;
import com.psja.CheckOrchestration.dto.SERVICE_NAME;
import com.psja.CheckOrchestration.dto.TransactionDTO;
import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.request.CreateOrderRequest;
import com.psja.CheckOrchestration.response.OrderResponse;
import com.psja.CheckOrchestration.response.PopResponse;
import com.psja.CheckOrchestration.service.OrderService;
import com.psja.CheckOrchestration.service.TransactionMaintainenceService;
import com.psja.CheckOrchestration.util.JsonUtility;

@Service("ORDER_SERVICE")
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	private TransactionMaintainenceService transactionMaintainenceService;
	@Autowired
	private RestHandler restHandler;
	@Autowired
	private JsonUtility jsonUtility;
	
	private String transactionNumber;
	
	public void createOrder( CreateOrderRequest createOrderRequest )throws Exception {
		
		try {
			callOrderService( createOrderRequest );
			callPaymentService();
		} catch( Exception exp ) {
			generatePOPOperation();
		}
		
	}
	
	private final void callOrderService( CreateOrderRequest createOrderRequest )throws Exception {
		TransactionDTO transactionDTO = new TransactionDTO();
		List<TransactionDTO.ChildTransactionDTO> childTransactionDTOList = new ArrayList<>();
		assembleTransactionDTO( transactionDTO, SERVICE_NAME.ORDER_SERVICE, PURPOSE.ORDER, childTransactionDTOList );
		transactionMaintainenceService.newSave(transactionDTO);
		
			
		String response = restHandler.sendData(createOrderRequest, "http://localhost:8082/order_service/createOrder");
		OrderResponse orderResponse = new OrderResponse();
		orderResponse = (OrderResponse)jsonUtility.readJsonString(response, orderResponse);
		
	}
	
	private void callPaymentService() {
		
	}
	
	private final void assembleTransactionDTO( TransactionDTO transactionDTO, SERVICE_NAME serviceName,
												PURPOSE purpose, List<TransactionDTO.ChildTransactionDTO> childTransactionDTOList ) {
		
		transactionNumber = UUID.randomUUID().toString();
		transactionDTO.setTransactionId( transactionNumber );
		TransactionDTO.ChildTransactionDTO childTransactionDTO = new TransactionDTO.ChildTransactionDTO();
		childTransactionDTO.setServiceName( serviceName );
		childTransactionDTO.setPurpose( purpose );
		childTransactionDTOList.add( childTransactionDTO );
		transactionDTO.setChildTransactionDTOList(childTransactionDTOList);
	}
	
	private final void generatePOPOperation()throws Exception {
		List<TransactionChildEntity> transactionChildEntityList = transactionMaintainenceService.retrieveInformationUsingTransactionId(transactionNumber);
	
		for ( TransactionChildEntity transactionChildEntity: transactionChildEntityList ) {
			if ( transactionChildEntity.getServiceName().equalsIgnoreCase(SERVICE_NAME.ORDER_SERVICE.name()) ) {
				if ( transactionChildEntity.getPurpose().equalsIgnoreCase( PURPOSE.ORDER.name() ) ) {
					String response = restHandler.getData("http://localhost:8082/order_service/cancelOrder");
					PopResponse popResponse = new PopResponse();
					popResponse = (PopResponse)jsonUtility.readJsonString(response, popResponse);
					if ( popResponse.getStatus().equals("SUCCESS") ) {
						transactionMaintainenceService.deleteFromChildTableUsingId( transactionChildEntity.getId().toString() );
					}
				}
			}
		}
	}
}
