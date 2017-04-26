package org.giste.club.server.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 7518450465957129917L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public BaseEntity() {
		
	}
	
	public BaseEntity(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
