package com.zerobase.cms.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jxxdxmxx2@gmail.com");  // 보내는 사람
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }
} 