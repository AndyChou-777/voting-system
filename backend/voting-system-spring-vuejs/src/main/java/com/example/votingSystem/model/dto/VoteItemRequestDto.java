package com.example.votingSystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteItemRequestDto {
	
	@NotBlank(message = "項目名稱不能為空")
    @Size(max = 50, message = "項目名稱不能超過 50 個字符")
    private String itemName;

}
