package com.psja.CheckOrchestration.repo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.psja.CheckOrchestration.entity.TransactionChildEntity;
import com.psja.CheckOrchestration.entity.TransactionParentEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


@Repository
public class BaseTransactionParentRepo /*extends SimpleJpaRepository< TransactionParentEntity, Integer >*/ implements TransactionParentRepo{

	@Autowired
	private EntityManager em;
	
//	public BaseTransactionParentRepo(JpaEntityInformation<TransactionParentEntity, ?> entityInformation,
//			EntityManager entityManager) {
//		super(entityInformation, entityManager);
//		
//	}

	@Override
	@Transactional
	public void save(TransactionParentEntity transactionParentEntity) {
		em.persist(transactionParentEntity);
	}
	
	@Override
	public List<Object[]> getByTransactionIdFromChildTable( String transactionId ) {
		Query query = em.createNativeQuery("SELECT * FROM TRANSACTION_CHILD_TABLE where transaction_id = '"+transactionId+"'");
		List<Object[]> objList = query.getResultList();
		return objList;
	}
	
	@Override
	@Transactional
	public void deleteByIdFromChildTable( String id ) {
		Query query = em.createNativeQuery( "DELETE FROM TRANSACTION_CHILD_TABLE where id = "+ id );
		query.executeUpdate();
	}
	
	@Override
	public TransactionParentEntity getByTransactionId( String transactionId ){
		
		Query query = em.createNativeQuery( "select * from TRANSACTION_PARENT_TABLE where transaction_id = '" +transactionId +"'" );
		List<Object[]> parentObjList = query.getResultList();
		List<Object[]> childObjList = getByTransactionIdFromChildTable( transactionId );
		
		TransactionParentEntity transactionParentEntity = new TransactionParentEntity();
		transactionParentEntity.setId( Integer.parseInt( parentObjList.get(0)[0].toString()) );
		transactionParentEntity.setTransactionId( parentObjList.get(0)[1].toString() );
		
		List<TransactionChildEntity> transactionChildEntityList = new ArrayList<>();
		for ( Object[] obj: childObjList ) {
			TransactionChildEntity transactionChildEntity = new TransactionChildEntity();
			transactionChildEntity.setId( Integer.parseInt( obj[0].toString() ) );
			transactionChildEntity.setPurpose( obj[1].toString() );
			transactionChildEntity.setServiceName( obj[2].toString() );
			transactionChildEntity.setTransactionParentEntity( transactionParentEntity );
			transactionChildEntityList.add(transactionChildEntity);
		}
		
		transactionParentEntity.setTransactionChildEntityList(transactionChildEntityList);
		return transactionParentEntity;
	} 
	
	@Override
	@Transactional
	public void merge( TransactionParentEntity transactionParentEntity ) {
		em.merge( transactionParentEntity );
	}

}
