package com.psja.CheckOrchestration.assembler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.psja.CheckOrchestration.dto.TransactionDTO;
import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.entity.TransactionParentEntity;

@Component
public class TransactionParentAssembler {

	public TransactionParentEntity toDomainObject( TransactionDTO transactionDTO ) {
		
		TransactionParentEntity transactionParentEntity = new TransactionParentEntity();
		transactionParentEntity.setTransactionId( transactionDTO.getTransactionId() );
		List<TransactionChildEntity> transactionChildEntityList = new ArrayList<>();
		//TransactionChildEntity transactionChildEntity = new TransactionChildEntity();
		assembleTransactionChildEntityList( transactionDTO, transactionChildEntityList, transactionParentEntity );
		
		//transactionChildEntityList.add( transactionChildEntity );
		
		transactionParentEntity.setTransactionChildEntityList(transactionChildEntityList);
		return transactionParentEntity;
	}
	
	public void assembleTransactionChildEntityList( TransactionDTO transactionDTO, 
																		List<TransactionChildEntity> transactionChildEntityList,
														TransactionParentEntity transactionParentEntity ) {
		
		for ( TransactionDTO.ChildTransactionDTO childTransactionDTO: transactionDTO.getChilTransactionDTOList() ) {
			TransactionChildEntity transactionChildEntity = new TransactionChildEntity();
			transactionChildEntity.setPurpose( childTransactionDTO.getPurpose() );
			transactionChildEntity.setServiceName( childTransactionDTO.getServiceName() );
			transactionChildEntity.setId( childTransactionDTO.getId() );
			transactionChildEntity.setTransactionParentEntity(transactionParentEntity);
			transactionChildEntityList.add( transactionChildEntity );
		}
		
	}
	
	
	
	
}
