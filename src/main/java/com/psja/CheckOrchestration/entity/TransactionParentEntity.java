package com.psja.CheckOrchestration.entity;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "TRANSACTION_PARENT_TABLE")
public class TransactionParentEntity implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer id;
	@Column(name = "transaction_id", nullable = false, unique = true)
	private String transactionId;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionParentEntity", fetch = FetchType.EAGER )
	private List<TransactionChildEntity> transactionChildEntityList = new ArrayList<>();
	
	public void setId( Integer id ) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}
	
	public String getTransactionId() {
		return this.transactionId;
	}
	public void setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
	}
	public void setTransactionChildEntityList( List<TransactionChildEntity> transactionChildEntityList ) {
		this.transactionChildEntityList = transactionChildEntityList;
	}
	public void add( TransactionChildEntity transactionChildEntity ) {
		if ( transactionChildEntity == null ) {
			transactionChildEntity = new TransactionChildEntity();
		}
		this.transactionChildEntityList.add( transactionChildEntity );
	}
	public List<TransactionChildEntity> getTransactionChildEntityList(){
		return this.transactionChildEntityList;
	}
	
}
