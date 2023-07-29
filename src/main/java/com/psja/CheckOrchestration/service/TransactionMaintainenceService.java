package com.psja.CheckOrchestration.service;

import java.util.List;

import com.psja.CheckOrchestration.dto.TransactionDTO;
import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.entity.TransactionParentEntity;

public interface TransactionMaintainenceService {

	public void newSave( TransactionDTO transactionDTO )throws Exception;
	
	public List<TransactionChildEntity> retrieveInformationUsingTransactionId( String transactionId )throws Exception;
	
	public void deleteFromChildTableUsingId( String id )throws Exception;
	
	public void retrieveOldRecordUsingTransactionId( TransactionDTO.ChildTransactionDTO childTransactionDTO, 
															String transactionId )throws Exception;
}
