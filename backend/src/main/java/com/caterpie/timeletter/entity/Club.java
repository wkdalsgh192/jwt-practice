package com.caterpie.timeletter.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
//import io.swagger.annotations.ApiParam;
import lombok.Data;

@Entity
public class Club {

	@Id
	private int clubId;
	@Column(length=10,nullable =false)
	private String clubName;
	private int user_id;
	
	
	@Builder
	public Club(String clubName) {
		super();
		this.clubName = clubName;
	}
	
	
	
}
