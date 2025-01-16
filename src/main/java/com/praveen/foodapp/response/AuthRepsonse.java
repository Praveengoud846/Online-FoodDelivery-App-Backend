package com.praveen.foodapp.response;

import com.praveen.foodapp.model.USER_ROLE;

import lombok.Data;

@Data
public class AuthRepsonse {
	
	private String jwt;
	private String message;
	private USER_ROLE role;
}
