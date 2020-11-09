package co.inventorsoft.jdbc.service;

import co.inventorsoft.jdbc.model.Mail;

import java.util.List;

public interface MailService {
    List<Mail> readList();
    void create(Mail mail);
    Mail read(long id);
    void delete(long id);
}
