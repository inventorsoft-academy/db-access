package com.example.demo.service;

import com.example.demo.model.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {

    private JavaMailSender mailSender;


    public void send(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(message.getEmail_to());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getEmail_text());

        mailSender.send(mailMessage);
    }
}
