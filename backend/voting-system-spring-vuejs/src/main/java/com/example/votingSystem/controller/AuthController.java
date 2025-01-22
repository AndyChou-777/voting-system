package com.example.votingSystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votingSystem.model.dto.UserLoginDto;
import com.example.votingSystem.model.dto.UserRegistrationDto;
import com.example.votingSystem.model.dto.UserResponeDto;
import com.example.votingSystem.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/*
 * WEB REST API
 * ----------------------------------
 * POST /register   註冊
 * POST /login      登入
 * GET  /logout     登出
 * GET  /session    獲得憑證
 * */

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        try {
        	userService.registerUser(userRegistrationDto);
        	return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto loginDto, 
                                 HttpSession session) {
        Optional<UserResponeDto> optLoginDto = userService.login(loginDto);
        if (optLoginDto.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("帳號或密碼錯誤");
        }
        
        UserResponeDto userResponeDto = optLoginDto.get();
        session.setAttribute("loginDTO", userResponeDto);
        
        return ResponseEntity.ok(userResponeDto);
    }
    
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("登出成功");
    }
    
    @GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpSession session) {
    	
    	UserResponeDto loginDto = (UserResponeDto) session.getAttribute("loginDTO");
        
        if (loginDto == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("未登入");
        }
        return ResponseEntity.ok(loginDto);
    }
    
}
