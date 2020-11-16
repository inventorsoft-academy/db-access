package co.inventorsoft.jdbc.repository;

import co.inventorsoft.jdbc.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepo extends JpaRepository<Mail,Long> {
}
