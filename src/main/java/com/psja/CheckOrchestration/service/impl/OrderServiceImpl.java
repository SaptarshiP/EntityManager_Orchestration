package com.psja.CheckOrchestration.service.impl;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.psja.CheckOrchestration.common.RestHandler;
import com.psja.CheckOrchestration.common.SystemException;
import com.psja.CheckOrchestration.dto.DeliveryRequestDTO;
import com.psja.CheckOrchestration.dto.PURPOSE;
import com.psja.CheckOrchestration.dto.PaymentRequestDTO;
import com.psja.CheckOrchestration.dto.SERVICE_NAME;
import com.psja.CheckOrchestration.dto.TransactionDTO;
import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.request.CreateOrderRequest;
import com.psja.CheckOrchestration.response.OrderResponse;
import com.psja.CheckOrchestration.response.PaymentSuccessResponse;
import com.psja.CheckOrchestration.response.PopResponse;
import com.psja.CheckOrchestration.service.OrderService;
import com.psja.CheckOrchestration.service.TransactionMaintainenceService;
import com.psja.CheckOrchestration.util.JsonUtility;

@Service("ORDER_SERVICE")
public class OrderServiceImpl implements OrderService {

	@Value("${error.transactionid.not.present.error.code}")
	private String TRANSACTION_ID_NOT_PRESENT_ERROR_CODE;
	@Value("${error.transactionid.not.present.error.message}")
	private String TRANSACTION_ID_NOT_PRESENT_ERROR_MESSAGE;
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
			callPaymentService( createOrderRequest );
			callDeliveryService( createOrderRequest );
			this.transactionNumber = null;
		} catch( Exception exp ) {
			this.transactionNumber = null;
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
		orderResponse = (OrderResponse)jsonUtility.readJsonString(response, OrderResponse.class);
		
	}
	
	private void callPaymentService( CreateOrderRequest createOrderRequest )throws Exception {
		
		System.out.println("INSERTING INTO callPaymentService");
		TransactionDTO transactionDTO = new TransactionDTO();
		List<TransactionDTO.ChildTransactionDTO> childTransactionDTOList = new ArrayList<>();
		
		//assembleTransactionDTO( transactionDTO, SERVICE_NAME.PAYMENT_SERVICE, PURPOSE.PAYMENT, childTransactionDTOList );
		TransactionDTO.ChildTransactionDTO childTransactionDTO = new TransactionDTO.ChildTransactionDTO();
		childTransactionDTO.setPurpose( PURPOSE.PAYMENT );
		childTransactionDTO.setServiceName( SERVICE_NAME.PAYMENT_SERVICE );
		//transactionMaintainenceService.newSave(transactionDTO);
		transactionMaintainenceService.retrieveOldRecordUsingTransactionId( childTransactionDTO, transactionNumber );
		PaymentRequestDTO paymentRequestDTO = createPaymenRequestData( createOrderRequest );
		String data = restHandler.sendData( paymentRequestDTO, "http://localhost:8085/createPayment" );
		PaymentSuccessResponse paymentSuccessResponse = (PaymentSuccessResponse)jsonUtility.readJsonString(data, PaymentSuccessResponse.class);
		
		System.out.println("EXITING FROM callPaymentService");
	}
	
	private void callDeliveryService( CreateOrderRequest createOrderRequest )throws Exception{
		
		System.out.println( "Inserting intp callDeliveryService" );
		TransactionDTO transactionDTO = new TransactionDTO();
		List<TransactionDTO.ChildTransactionDTO> childTransactionDTOList = new ArrayList<>();
		assembleTransactionDTO( transactionDTO, SERVICE_NAME.DELIVERY_SERVICE, PURPOSE.DELIVERY, childTransactionDTOList );
		transactionMaintainenceService.newSave(transactionDTO);
		
		DeliveryRequestDTO deliveryRequestDTO = createDeliveryRequestData( createOrderRequest );
		String response = restHandler.sendData(deliveryRequestDTO, "http://localhost:8087/delivery/create_delivery");
		
		
		System.out.println( "Exiting from callDeliveryService" );
		
	}
	
	private final void assembleTransactionDTO( TransactionDTO transactionDTO, SERVICE_NAME serviceName,
												PURPOSE purpose, List<TransactionDTO.ChildTransactionDTO> childTransactionDTOList ) {
		
		transactionNumber = transactionNumber!=null? transactionNumber : UUID.randomUUID().toString();
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
					popResponse = (PopResponse)jsonUtility.readJsonString(response, PopResponse.class);
					if ( popResponse.getStatus().equals("SUCCESS") ) {
						transactionMaintainenceService.deleteFromChildTableUsingId( transactionChildEntity.getId().toString() );
					}
				}
			} else if ( transactionChildEntity.getServiceName().equalsIgnoreCase( SERVICE_NAME.PAYMENT_SERVICE.name() ) ) {
				
				if ( transactionChildEntity.getPurpose().equals(PURPOSE.PAYMENT.name()) ) {
					String response = restHandler.cancelUsingTransactionId("http://localhost:8085/cancelPayment?transaction_id="+transactionChildEntity.getTransactionParentEntity().getTransactionId());
					if (response != null) {
						PopResponse popResponse = (PopResponse)jsonUtility.readJsonString(response, PopResponse.class);
						if ( popResponse.getStatus().equalsIgnoreCase("SUCCESS") ) {
							transactionMaintainenceService.deleteFromChildTableUsingId( transactionChildEntity.getId().toString() );
						}
					}
				}
			} else if ( transactionChildEntity.getServiceName().equalsIgnoreCase( SERVICE_NAME.DELIVERY_SERVICE.name() ) ) {
				if ( transactionChildEntity.getPurpose().equalsIgnoreCase( PURPOSE.DELIVERY.name()) ) {
					String response = restHandler.cancelUsingTransactionId("http://localhost:8087/delivery/cancel_delivery?transaction_id="+transactionChildEntity.getTransactionParentEntity().getTransactionId());
					if (response != null) {
						PopResponse popResponse = (PopResponse)jsonUtility.readJsonString(response, PopResponse.class);
						if ( popResponse.getStatus().equalsIgnoreCase("SUCCESS") ) {
							transactionMaintainenceService.deleteFromChildTableUsingId( transactionChildEntity.getId().toString() );
						}
					} 
				}
			}
		}
	}
	
	private PaymentRequestDTO createPaymenRequestData( CreateOrderRequest createOrderRequest )throws Exception {
	
		System.out.println("INSERTING INTO createPaymenRequestData");
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
		
		paymentRequestDTO.setPaymentAmount( createOrderRequest.getOrderPrice() );
		paymentRequestDTO.setPaymentType( createOrderRequest.getPaymentMode().name() );
		if ( this.transactionNumber != null ) {
			paymentRequestDTO.setTransactionId( this.transactionNumber );
		}else
			throw new SystemException( new Exception( TRANSACTION_ID_NOT_PRESENT_ERROR_CODE ), TRANSACTION_ID_NOT_PRESENT_ERROR_CODE,
										TRANSACTION_ID_NOT_PRESENT_ERROR_MESSAGE );
					
		System.out.println( "EXITING FROM createPaymenRequestData" );
		return paymentRequestDTO;
	}
	
	private DeliveryRequestDTO createDeliveryRequestData( CreateOrderRequest createOrderRequest ) {
		
		DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
		
		deliveryRequestDTO.setDeliveryPartnerName( createOrderRequest.getDeliveryPartnerName().name() );
		deliveryRequestDTO.setGoodsType( createOrderRequest.getOrderItemType() );
		deliveryRequestDTO.setTransactionId( transactionNumber );
		
		return deliveryRequestDTO;
	}
}
