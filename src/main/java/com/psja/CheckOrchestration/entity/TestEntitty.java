package com.psja.CheckOrchestration.entity;


import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "TEST_ENTITY")
public class TestEntitty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "NAME")
	private String name;
	
	public void setId( String id ) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName( String name ) {
		this.name = name;
	}
}
