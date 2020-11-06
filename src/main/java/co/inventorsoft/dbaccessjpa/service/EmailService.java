package co.inventorsoft.dbaccessjpa.service;

import co.inventorsoft.dbaccessjpa.model.Email;
import co.inventorsoft.dbaccessjpa.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Transactional
    public void saveEmail(Email email) {
        emailRepository.save(email);
    }

    @Transactional
    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    @Transactional
    public Email findById(Long id){
        return emailRepository.getOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        emailRepository.deleteById(id);
    }

    @Transactional
    public List<Email> mailsToSend() {
        List<Email> emailList = findAll();
        return emailList.stream()
                .filter(email -> email.getDate().equals(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
