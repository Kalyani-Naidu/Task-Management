package com.example.jwt.auth.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.auth.entity.Users;
import com.example.jwt.auth.payload.UserDto;
import com.example.jwt.auth.repository.UserRepository;
import com.example.jwt.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
	
		Users user = new Users();
		BeanUtils.copyProperties(userDto, user, "password");    // converting dto to entity obj
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users savedUser = userRepo.save(user);
		UserDto response = new UserDto();
		BeanUtils.copyProperties(savedUser, response);    // converting entity to dto obj
		return response;
	}
	
	

}
