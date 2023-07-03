package com.psja.CheckOrchestration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
	

}
