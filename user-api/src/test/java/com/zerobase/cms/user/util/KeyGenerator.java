package com.zerobase.cms.user.util;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate JWT Secret Key
        byte[] jwtKey = new byte[32];
        new SecureRandom().nextBytes(jwtKey);
        String jwtSecret = Base64.getEncoder().encodeToString(jwtKey);
        
        // Generate Encryption Key
        byte[] encKey = new byte[32];
        new SecureRandom().nextBytes(encKey);
        String encryptionKey = Base64.getEncoder().encodeToString(encKey);
        
        System.out.println("JWT Secret Key: " + jwtSecret);
        System.out.println("Encryption Key: " + encryptionKey);
    }
}
