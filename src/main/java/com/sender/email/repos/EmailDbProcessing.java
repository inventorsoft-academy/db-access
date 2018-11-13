package com.sender.email.repos;

import com.sender.email.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Repository
@Profile("JDBC")
public class EmailDbProcessing implements EmailProcessing {
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmailDbProcessing(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Email> getAll() {
        return jdbcTemplate.query("SELECT * FROM local_db.emails", new EmailMapper());
    }

    @Override
    public List<Email> getUnsent() {
        return jdbcTemplate.query("SELECT * FROM local_db.emails WHERE is_sent = 0", new EmailMapper());
    }

    @Override
    public void addNewEmail(Email email) {
        jdbcTemplate.update("INSERT INTO local_db.emails (recipient, subject, body, delivery_date, is_sent) " +
                "VALUES(?,?,?,?,?)", email.getRecipient(), email.getSubject(),
                email.getBody(), email.getDeliveryDate(), false);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM local_db.emails WHERE id = ?", id);
    }

    @Override
    public void changeDate(int id, Date newDate) {
        jdbcTemplate.update("UPDATE local_db.emails SET delivery_date = ? WHERE id = ?",newDate, id);
    }

    @Override
    public void changeStatus(int id) {
        jdbcTemplate.update("UPDATE local_db.emails SET is_sent = 1 WHERE id = ?", id);
    }
}
