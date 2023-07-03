package com.psja.CheckOrchestration.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import java.io.Serializable;

@Entity
@Table(name = "TRANSACTION_CHILD_TABLE")
public class TransactionChildEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JoinColumn( name = "transaction_id", referencedColumnName = "transaction_id", nullable = false )
	@ManyToOne( cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY )
	private TransactionParentEntity transactionParentEntity;
	
	@Column(name = "purpose")
	private String purpose;
	@Column( name = "service_name" )
	private String serviceName;
	
	public Integer getId() {
		return this.id;
	}
	public void setId( Integer id ) {
		this.id = id;
	}
	
	public TransactionParentEntity getTransactionParentEntity() {
		return this.transactionParentEntity;
	}
	public void setTransactionParentEntity( TransactionParentEntity transactionParentEntity ) {
		this.transactionParentEntity = transactionParentEntity;
	}
	
	public String getPurpose() {
		return this.purpose;
	}
	public void setPurpose( String purpose ) {
		this.purpose = purpose;
	}
	
	public String getServiceName() {
		return this.serviceName;
	}
	public void setServiceName( String serviceName ) {
		this.serviceName = serviceName;
	}
	
}
