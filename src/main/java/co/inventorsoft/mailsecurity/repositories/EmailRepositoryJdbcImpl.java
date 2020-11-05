package co.inventorsoft.mailsecurity.repositories;

import co.inventorsoft.mailsecurity.models.Email;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmailRepositoryJdbcImpl implements EmailRepository {

    private Connection connection;

    public EmailRepositoryJdbcImpl(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Email> findAll() {
        String SQL_SELECT_ALL = "SELECT * FROM emails";
        try {
            List<Email> emails = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String recipient = resultSet.getString("recipient");
                String subject = resultSet.getString("subject");
                String body = resultSet.getString("body");
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                Email email = new Email(id, recipient, subject, body, date);
                emails.add(email);
            }
            return emails;
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveMail(Email email) {
        String SQL_INSERT = "INSERT INTO emails(recipient,subject, body, date) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            preparedStatement.setString(1, email.getRecipient());
            preparedStatement.setString(2, email.getSubject());
            preparedStatement.setString(3, email.getBody());
            preparedStatement.setString(4, email.getDate().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Email email) {
        String SQL_UPDATE = "UPDATE emails SET recipient=?, subject=?, body=?, date=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, email.getRecipient());
            preparedStatement.setString(2, email.getSubject());
            preparedStatement.setString(3, email.getBody());
            preparedStatement.setString(4, email.getDate().toString());
            preparedStatement.setInt(5, email.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Email findById(int id) {
        String SQL_FIND_BY_ID = "SELECT * FROM emails WHERE id=?";
        Email email = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String recipient = resultSet.getString("recipient");
                String subject = resultSet.getString("subject");
                String body = resultSet.getString("body");
                LocalDateTime date = LocalDateTime.parse(resultSet.getString("date"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                email = new Email(id, recipient, subject, body, date);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return email;
    }

    @Override
    public void delete(Email email) {
        String SQL_DELETE = "DELETE FROM emails WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, email.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Email> mailsToSend() {
        List<Email> emailList = findAll();
        return emailList.stream()
                .filter(email -> email.getDate().equals(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
