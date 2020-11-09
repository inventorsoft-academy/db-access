package co.inventorsoft.jdbc.repository;

import co.inventorsoft.jdbc.model.Mail;

import java.util.List;

public interface MailDao {
    List<Mail> readList();
    void create(Mail mail);
    Mail read(long id);
    void delete(long id);
}
