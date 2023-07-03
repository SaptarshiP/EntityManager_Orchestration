package com.psja.CheckOrchestration.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psja.CheckOrchestration.assembler.TransactionParentAssembler;
import com.psja.CheckOrchestration.common.SystemException;
import com.psja.CheckOrchestration.dto.TransactionDTO;
import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.entity.TransactionParentEntity;
import com.psja.CheckOrchestration.repo.TransactionParentRepo;
import com.psja.CheckOrchestration.service.TransactionMaintainenceService;

@Service
public class TransactionMaintainenceServiceImpl implements TransactionMaintainenceService{
	
	@Value("${error.transactionId.not.given.code}")
	private String TRANSACTION_ID_ABSENT_ERROR_CODE;
	@Value("${error.transactionId.not.given.message}")
	private String TRANSACTION_ID_ABSENT_ERROR_MESSAGE;
	@Value("${error.id.not.given.code}")
	private String ID_ABSENT_ERROR_CODE;
	@Value("${error.id.not.given.message}")
	private String ID_ABSENT_ERROR_MESSAGE;
	
	@Autowired
	private TransactionParentAssembler transactionParentAssembler;
	@Autowired
	private TransactionParentRepo transactionParentRepo;
	
	private void preValidateForNewSave( TransactionDTO transactionDTO )throws SystemException {
	
		if ( "".equalsIgnoreCase( transactionDTO.getTransactionId() ) ||  
				!(transactionDTO.getTransactionId() != null) ) {
			throw new SystemException( new Exception(TRANSACTION_ID_ABSENT_ERROR_MESSAGE),
					TRANSACTION_ID_ABSENT_ERROR_CODE, TRANSACTION_ID_ABSENT_ERROR_MESSAGE );
		}
	}
	
	private void persistForNewSave( TransactionParentEntity transactionParentEntity )throws Exception {
		transactionParentRepo.save( transactionParentEntity );
	}
	
	@Override
	public void newSave( TransactionDTO transactionDTO )throws Exception {
		
		preValidateForNewSave( transactionDTO );
		TransactionParentEntity transactionParentEntity = transactionParentAssembler.toDomainObject(transactionDTO);
		persistForNewSave( transactionParentEntity );
		
	}
	
	private void prevalidateForRetrieveChildTableDataUsingTransactionId( String transactionId )throws Exception {
		if ( transactionId == null || "".equals(transactionId) ) {
			throw new SystemException( new Exception(TRANSACTION_ID_ABSENT_ERROR_MESSAGE),
					TRANSACTION_ID_ABSENT_ERROR_CODE, TRANSACTION_ID_ABSENT_ERROR_MESSAGE );
		}
	}
	
	private List<TransactionChildEntity> persistForRetrieveChildTableDataUsingTransactionId( String transactionId ) {
		List<Object[]> objList = transactionParentRepo.getByTransactionIdFromChildTable(transactionId);
		List<TransactionChildEntity> transactionChildEntityList = new ArrayList<>();
		
		for ( Object[] obj: objList ) {
			TransactionChildEntity transactionChildEntity = new TransactionChildEntity();
			transactionChildEntity.setId( (Integer)obj[0] );
			transactionChildEntity.setPurpose( (String)obj[1] );
			transactionChildEntity.setServiceName( (String)obj[2] );
			transactionChildEntityList.add(transactionChildEntity);
		}
		
		return transactionChildEntityList;
	}
	
	@Override
	public List<TransactionChildEntity> retrieveInformationUsingTransactionId( String transactionId )throws Exception {
		prevalidateForRetrieveChildTableDataUsingTransactionId( transactionId );
		return persistForRetrieveChildTableDataUsingTransactionId( transactionId );
	}
	
	private void prevalidateForDeleteFromChildTable( String id )throws Exception {
		if ( id == null )
			throw new SystemException( new Exception( ID_ABSENT_ERROR_CODE ), ID_ABSENT_ERROR_CODE,
												ID_ABSENT_ERROR_MESSAGE	);
	}
	private void persistForDeleteFromChildTable( String id ) {
		transactionParentRepo.deleteByIdFromChildTable(id);
	}
	
	@Override
	public void deleteFromChildTableUsingId( String id )throws Exception{
		prevalidateForDeleteFromChildTable( id );
		persistForDeleteFromChildTable( id );
	}

}
