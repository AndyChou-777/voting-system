package com.example.votingSystem.service;

import java.util.List;

import com.example.votingSystem.model.dto.VoteItemDto;
import com.example.votingSystem.model.dto.VoteRecordDto;

public interface VotingService {

	public List<VoteItemDto> getAllVoteItems();
	
	public void addVoteItem(String itemName);
	
	public void updateVoteItem(Long itemId, String newName);
	
	public void deleteVoteItem(Long itemId);
	
	public void addVoteRecord(Long userId, List<Long> voteItemIds);
	
	public List<Object[]> getVoteRecordsByUserId(Long userId);
	
}
