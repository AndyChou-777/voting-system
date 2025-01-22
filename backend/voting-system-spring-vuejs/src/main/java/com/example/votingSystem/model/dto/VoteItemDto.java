package com.example.votingSystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteItemDto {
	
	private Long id;
    private String itemName;
    private Integer voteCount;

}
