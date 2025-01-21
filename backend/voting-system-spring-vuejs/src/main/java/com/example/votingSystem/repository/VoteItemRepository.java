package com.example.votingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.votingSystem.model.entity.VoteItem;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

    @Procedure(name = "GetAllVoteItems")
    List<VoteItem> getAllVoteItems();

    @Procedure(name = "AddVoteItem")
    void addVoteItem(@Param("itemName") String itemName);

    @Procedure(name = "UpdateVoteItem")
    void updateVoteItem(@Param("itemId") Long itemId, @Param("newName") String newName);

    @Procedure(name = "DeleteVoteItem")
    void deleteVoteItem(@Param("itemId") Long itemId);
	
}
