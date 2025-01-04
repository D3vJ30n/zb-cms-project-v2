package com.zerobase.cms.user.config;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;

@TestConfiguration
public class UserTestConfiguration {
    
    @Bean
    @Primary
    public MailgunClient mailgunClient() {
        return new MailgunClient() {
            @Override
            public ResponseEntity<String> sendEmail(SendMailForm form) {
                return ResponseEntity.ok("Test Response");
            }
        };
    }
} 