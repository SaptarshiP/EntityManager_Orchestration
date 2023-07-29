package com.psja.CheckOrchestration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.psja.CheckOrchestration.entity.TestEntitty;
import com.psja.CheckOrchestration.entity.TransactionParentEntity;


public interface TransactionParentRepo {//extends JpaRepository<TransactionParentEntity, Integer>{

	public void save( TransactionParentEntity transactionParentEntity );
	public List<Object[]> getByTransactionIdFromChildTable( String transactionId );
	public void deleteByIdFromChildTable( String id );
	public TransactionParentEntity getByTransactionId( String transactionId );
	public void merge( TransactionParentEntity transactionParentEntity );
}
