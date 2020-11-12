package com.hometask.task_security.service;


import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.EmailRepo;
import com.hometask.task_security.repository.db_repo.JDBCRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;
    private final EmailRepo repo;


    @Autowired
    public EmailService(@Qualifier("getJavaMailSender") JavaMailSender emailSender, JDBCRepoImpl repo) {
        this.emailSender = emailSender;
        this.repo = repo;

    }

    @Scheduled(fixedRate = 60000) //1 min
    public void sendSimpleEmail() throws MailException, IOException {
        List<Email> emailsForSending = repo.findEmailsForSending();
        for (Email emails : emailsForSending) {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(emails.getRecipient());
            message.setSubject(emails.getSubject());
            message.setText(emails.getSubject());

            LOG.info("Emails information {}:", emails.toString());
            this.emailSender.send(message);
        }
        repo.deleteById();
    }
}
