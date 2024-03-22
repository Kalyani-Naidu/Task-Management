package com.example.jwt.auth.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JwtAuthResponse {

	private String token;
	private String tokenType = "Bearer";
	
	public JwtAuthResponse(String token) {
		this.token = token;
	}
}
