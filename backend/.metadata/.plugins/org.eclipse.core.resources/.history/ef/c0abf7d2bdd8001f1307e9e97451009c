package com.example.votingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.votingSystem.model.dto.VoteItemDto;
import com.example.votingSystem.model.dto.VoteItemRequestDto;
import com.example.votingSystem.model.dto.VoteRecordDto;
import com.example.votingSystem.model.dto.VoteRequestDto;
import com.example.votingSystem.service.VotingService;

@RestController
@RequestMapping("/api/voting")
@CrossOrigin(origins = "http://localhost:5173")
public class VotingController {

	@Autowired
    private VotingService votingService;
	
	// 獲取所有投票項目
    @GetMapping("/items")
    public ResponseEntity<List<VoteItemDto>> getAllVoteItems() {
    	System.out.println("收到獲取要求");
        try {
            List<VoteItemDto> items = votingService.getAllVoteItems();
            System.out.println("回傳獲取陣列: " + items );
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 新增投票項目
    @PostMapping("/items")
    public ResponseEntity<Void> addVoteItem(@RequestBody VoteItemRequestDto itemName) {
    	System.out.println("新增投票項目: " + itemName.getItemName());
        try {
            votingService.addVoteItem(itemName.getItemName());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 更新投票項目
    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateVoteItem(
            @PathVariable Long itemId,
            @RequestBody VoteItemRequestDto itemName) {
        try {
            votingService.updateVoteItem(itemId, itemName.getItemName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 刪除投票項目
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteVoteItem(@PathVariable Long itemId) {
        try {
            votingService.deleteVoteItem(itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 新增投票記錄
    @PutMapping("/records")
    public ResponseEntity<Void> addVoteRecords(@RequestBody VoteRequestDto voteRequest) {
        try {
        	System.out.println("新增投票紀錄: " + voteRequest);
            votingService.addVoteRecord(voteRequest.getUserId(), voteRequest.getVoteItemIds());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // 獲取用戶投票記錄
    @GetMapping("/records/user/{userId}")
    public ResponseEntity<List<VoteRecordDto>> getVoteRecordsByUserId(@PathVariable Long userId) {
        try {
            List<VoteRecordDto> records = votingService.getVoteRecordsByUserId(userId);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
}
