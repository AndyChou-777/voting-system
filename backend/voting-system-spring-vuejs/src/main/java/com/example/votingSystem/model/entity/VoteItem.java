package com.example.votingSystem.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vote_items")
public class VoteItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String itemName;
	
	@Column(nullable = false)
	private Integer voteCount = 0;
	
	@OneToMany(mappedBy = "voteItem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VoteRecord> voteRecords = new ArrayList<>();

	@Override
	public String toString() {
		return "VoteItem [id=" + id + ", itemName=" + itemName + ", voteCount=" + voteCount + "]";
	}
	
}
