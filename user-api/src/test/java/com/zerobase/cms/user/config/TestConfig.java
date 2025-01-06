package com.zerobase.cms.user.config;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.internet.MimeMessage;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.crypto.SecretKey;
import java.io.InputStream;

@TestConfiguration
public class TestConfig {

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor("test-secret-key-32-bytes-or-longer!!".getBytes());
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(SecretKey secretKey) {
        return new JwtAuthenticationProvider(secretKey);
    }

    @Bean
    public JavaMailSender javaMailSenderMock() {
        return new JavaMailSender() {
            @Override
            public void send(SimpleMailMessage simpleMessage) {
                System.out.println("Test email sent successfully to: " + simpleMessage.getTo()[0]);
            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) {
                System.out.println("Test: Multiple SimpleMessages sent");
            }

            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) {
                System.out.println("Test: MimeMessage sent");
            }

            @Override
            public void send(MimeMessage... mimeMessages) {
                System.out.println("Test: Multiple MimeMessages sent");
            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) {
                System.out.println("Test: MimeMessagePreparator sent");
            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) {
                System.out.println("Test: Multiple MimeMessagePreparators sent");
            }
        };
    }
}