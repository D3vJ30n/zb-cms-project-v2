package com.zerobase.domain.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Aes256Util {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static String secretKey;
    private static String iv;
    
    @Value("${encryption.key:#{null}}")
    public void setSecretKey(String key) {
        secretKey = key != null ? key : generateKey();
        iv = secretKey.substring(0, 16);
    }
    
    public static String generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public static String encrypt(String text) {
        try {
            if (text == null || text.isEmpty()) {
                throw new IllegalArgumentException("Text to encrypt cannot be null or empty");
            }
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage(), e);
        }
    }

    public static String decrypt(String cipherText) {
        try {
            if (cipherText == null || cipherText.isEmpty()) {
                throw new IllegalArgumentException("Text to decrypt cannot be null or empty");
            }
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decodeBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage(), e);
        }
    }
    
    // 키 생성용 임시 메서드
    public static void main(String[] args) {
        String key = generateKey();
        System.out.println("Generated AES-256 Key: " + key);
    }
}