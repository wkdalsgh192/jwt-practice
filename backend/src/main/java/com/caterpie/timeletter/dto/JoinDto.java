package com.caterpie.timeletter.dto;

import lombok.Data;

@Data
public class JoinDto {
	private String email;
	private String password;
	private String name;
	private String phone;
}