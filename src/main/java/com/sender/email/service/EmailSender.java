package com.sender.email.service;

import com.sender.email.models.Email;
import com.sender.email.repos.EmailProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSender {
    private JavaMailSender mailSender;
    private ThreadPoolTaskScheduler scheduler;
    private EmailProcessing emailProcessing;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender, ThreadPoolTaskScheduler scheduler, EmailProcessing fileProcessing) {
        this.mailSender = javaMailSender;
        this.scheduler = scheduler;
        this.emailProcessing = fileProcessing;
    }

    public void sendLast() {
        Email email = emailProcessing.getUnsent().get(emailProcessing.getUnsent().size()-1);
        System.out.println(email.getDeliveryDate());

        scheduler.schedule(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            emailProcessing.changeStatus(email.getId());
            mailSender.send(message);
        }, email.getDeliveryDate());
    }

    public void sendAll() {
        List<Email> emails = emailProcessing.getUnsent();

        emails.stream()
                .forEach( email -> {
                    scheduler.schedule(() -> {
                        SimpleMailMessage message = new SimpleMailMessage();
                        message.setTo(email.getRecipient());
                        message.setSubject(email.getSubject());
                        message.setText(email.getBody());
                        emailProcessing.changeStatus(email.getId());
                        mailSender.send(message);
                        System.out.println("DELIVERED\nRecipieint: " + email.getRecipient() + "\nSubject: " + email.getSubject() + "Date: " + email.getDeliveryDate());
                    }, email.getDeliveryDate());
                });

    }
}
