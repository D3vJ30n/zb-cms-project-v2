package com.zerobase.domain.util;

public class KeyGenerator {
    public static void main(String[] args) {
        String generatedKey = Aes256Util.generateKey();
        System.out.println("Generated AES-256 Key: " + generatedKey);
    }
}
