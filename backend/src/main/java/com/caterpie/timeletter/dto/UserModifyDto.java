package com.caterpie.timeletter.dto;

import lombok.Data;

@Data
public class UserModifyDto {
	private int userId;
	private String email;
	private String password;
	private String name;
	private String phone;
}
