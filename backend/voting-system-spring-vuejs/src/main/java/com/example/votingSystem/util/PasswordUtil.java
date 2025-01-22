package com.example.votingSystem.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

	    public static boolean verifyPassword(String rawPassword, String storedPasswordHash, String salt) {
	        String hashedPassword = hashPassword(rawPassword, salt);
	        return storedPasswordHash.equals(hashedPassword);
	    }

	    private static String hashPassword(String password, String salt) {
	        try {
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	            messageDigest.update((salt + password).getBytes());
	            byte[] hashedBytes = messageDigest.digest();
	            return Base64.getEncoder().encodeToString(hashedBytes);
	        } catch (NoSuchAlgorithmException e) {
	        	return e.getMessage();
	        }
	    }
	
}
