package co.inventorsoft.jdbc.service;

import co.inventorsoft.jdbc.model.Mail;
import co.inventorsoft.jdbc.repository.MailRepo;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MailService {

     private MailRepo mailRepo;
     JavaMailSender mailSender;

     public MailService(MailRepo mailRepo){
         this.mailRepo = mailRepo;
     }

    @Transactional
    public void saveEmail(Mail mail) {
        mailRepo.save(mail);
    }

    @Transactional
    public List<Mail> findAll() {
        return mailRepo.findAll();
    }

    @Transactional
    public Mail findById(Long id){
        return mailRepo.getOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        mailRepo.deleteById(id);
    }

    public void sendMail(Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getRecipient());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getBody());
        mailSender.send(mailMessage);
    }
}
