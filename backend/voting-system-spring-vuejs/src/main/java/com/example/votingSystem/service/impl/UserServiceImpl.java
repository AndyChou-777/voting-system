package com.example.votingSystem.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votingSystem.model.dto.UserLoginDto;
import com.example.votingSystem.model.dto.UserRegistrationDto;
import com.example.votingSystem.model.dto.UserResponeDto;
import com.example.votingSystem.model.entity.User;
import com.example.votingSystem.repository.UserRepository;
import com.example.votingSystem.service.UserService;
import com.example.votingSystem.util.Hash;
import com.example.votingSystem.util.PasswordUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void registerUser(UserRegistrationDto userRegistrationDTO) {
        try {
            String salt = Hash.getSalt();
            String hashedPassword = Hash.getHash(userRegistrationDTO.getPassword(), salt);

            userRepository.registerUser(
            	userRegistrationDTO.getEmail(),
                hashedPassword,
                salt,
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getRole().toString()
            );
        } catch (Exception e) {
            if (e.getMessage().contains("Email already exists")) {
                throw new RuntimeException("Email already registered");
            }
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<UserResponeDto> login(UserLoginDto loginRequestDto) {

        Optional<User> optUser = userRepository.findUserByEmail(loginRequestDto.getEmail());
        
        if (optUser.isPresent()) {
            User user = optUser.get();
            boolean isPasswordValid = PasswordUtil.verifyPassword(
                loginRequestDto.getPassword(), 
                user.getPasswordHash(), 
                user.getSalt()
            );
            
            if (isPasswordValid) {
                return Optional.of(modelMapper.map(user, UserResponeDto.class));
            }
        }
        
        return Optional.empty();
    }
    
}
