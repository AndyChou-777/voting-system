package com.example.votingSystem.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRecordDto {

	private Long id;
	private String username; 
    private String itemName; 
    private LocalDateTime voteTime;
	
}
