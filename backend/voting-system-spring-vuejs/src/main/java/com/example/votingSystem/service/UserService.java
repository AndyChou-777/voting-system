package com.example.votingSystem.service;

import java.util.Optional;

import com.example.votingSystem.model.dto.UserLoginDto;
import com.example.votingSystem.model.dto.UserRegistrationDto;
import com.example.votingSystem.model.dto.UserResponeDto;

public interface UserService {

	public void registerUser(UserRegistrationDto userRegistrationDto);
	
	public Optional<UserResponeDto> login(UserLoginDto loginDto);
	
}
