package com.example.votingSystem.model.dto;

import com.example.votingSystem.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

	@NotBlank(message = "電子信箱不能為空")
    @Email(message = "請輸入有效的電子信箱地址")
    @Size(max = 255, message = "電子信箱長度不能超過 255 個字符")
    private String email;
	
	@NotBlank(message = "密碼不能為空")
    @Size(min = 8, max = 24, message = "密碼長度需在 8 到 24 個字符之間")
    private String password;
	
}
