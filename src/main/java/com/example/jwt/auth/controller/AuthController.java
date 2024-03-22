package com.example.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.auth.payload.JwtAuthResponse;
import com.example.jwt.auth.payload.LoginDto;
import com.example.jwt.auth.payload.UserDto;
import com.example.jwt.auth.security.JwtTokenProvider;
import com.example.jwt.auth.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	//POST store the user in DB
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto) {
		org.springframework.security.core.Authentication authentication = 
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		System.out.println("authentication: "+authentication);
		
		String token = jwtTokenProvider.generateToken(authentication.getName());	//get the token
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return  ResponseEntity.ok(new JwtAuthResponse(token));
	}
}
