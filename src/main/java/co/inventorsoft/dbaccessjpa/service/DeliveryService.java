package co.inventorsoft.dbaccessjpa.service;

import co.inventorsoft.dbaccessjpa.model.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DeliveryService {

    private final EmailService emailService;
    JavaMailSender mailSender;

    Logger logger = Logger.getLogger(DeliveryService.class.getName());

    public DeliveryService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate  = 60000)
    private void sendEmail() {
        List<Email> emailsToSend = emailService.mailsToSend();
        emailsToSend.forEach(email -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email.getRecipient());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setText(email.getBody());
            logger.info("Log about mail:" + email.toString());
            mailSender.send(mailMessage);
        });
        emailsToSend.clear();
    }
}