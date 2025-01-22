package com.example.votingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.votingSystem.model.entity.VoteRecord;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    @Procedure(name = "AddVoteRecord")
    void addVoteRecord(@Param("userId") Long userId, @Param("voteItemId") Long voteItemId);

    @Procedure(name = "GetVoteRecordsByUserId")
    List<Object[]> getVoteRecordsByUserId(@Param("userId") Long userId);
	
}
