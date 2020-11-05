package co.inventorsoft.mailsecurity.repositories;

import co.inventorsoft.mailsecurity.models.Email;

import java.util.List;

public interface EmailRepository {
    List<Email> findAll();
    void saveMail(Email email);
    void delete(Email email);
    void update(Email email);
    Email findById(int id);
    List<Email> mailsToSend();
}
