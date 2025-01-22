package com.example.votingSystem.model.dto;

import com.example.votingSystem.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponeDto {

	private Long id;
    private Role role;
	
}
