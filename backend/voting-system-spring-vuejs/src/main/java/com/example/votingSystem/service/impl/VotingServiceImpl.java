package com.example.votingSystem.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.votingSystem.model.dto.VoteItemDto;
import com.example.votingSystem.model.dto.VoteRecordDto;
import com.example.votingSystem.model.entity.VoteItem;
import com.example.votingSystem.repository.VoteItemRepository;
import com.example.votingSystem.repository.VoteRecordRepository;
import com.example.votingSystem.service.VotingService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VotingServiceImpl implements VotingService {
	
	 	@Autowired
	    private VoteItemRepository voteItemRepository;

	    @Autowired
	    private VoteRecordRepository voteRecordRepository;
	    
	    @Autowired
	    private ModelMapper modelMapper;  

	    // 獲取所有投票項目
	    public List<VoteItemDto> getAllVoteItems() {
	    	 List<VoteItem> voteItems = voteItemRepository.getAllVoteItems();
	         return voteItems.stream()
	                 .map(voteItem -> modelMapper.map(voteItem, VoteItemDto.class))
	                 .collect(Collectors.toList());
	    }

	    // 新增投票項目
	    public void addVoteItem(String itemName) {
	        voteItemRepository.addVoteItem(itemName);
	    }

	    // 更新投票項目
	    public void updateVoteItem(Long itemId, String newName) {
	        voteItemRepository.updateVoteItem(itemId, newName);
	    }

	    // 删除投票項目
	    public void deleteVoteItem(Long itemId) {
	        voteItemRepository.deleteVoteItem(itemId);
	    }

	    // 新增投票紀錄
	    public void addVoteRecord(Long userId, List<Long> voteItemIds) {
		    	voteItemIds.forEach(voteItemId -> 
		        voteRecordRepository.addVoteRecord(userId, voteItemId)
		    );
	    }

	    // 獲取用戶的投票紀錄
	    public List<Object[]> getVoteRecordsByUserId(Long userId) {
	    	List<Object[]> rawResults = voteRecordRepository.getVoteRecordsByUserId(userId);
	        return rawResults;
	    }

}
