package com.example.votingSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.votingSystem.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Procedure(name = "RegisterUser")
    void registerUser(
        @Param("p_email") String email,
        @Param("p_password_hash") String passwordHash,
        @Param("p_salt") String salt,
        @Param("p_username") String username,
        @Param("p_role") String role
    );
	
	@Procedure(name = "FindUserByEmail")
    Optional<User> findUserByEmail(@Param("p_email") String email);
	
}
