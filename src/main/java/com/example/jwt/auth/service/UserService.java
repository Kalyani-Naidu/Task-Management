package com.example.jwt.auth.service;

import com.example.jwt.auth.payload.UserDto;

public interface UserService {

	public UserDto createUser(UserDto userDto);
	
}
