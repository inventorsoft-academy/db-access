package com.hometask.task_security.repository.db_repo;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.EmailRepo;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JDBCRepoImpl implements EmailRepo {

    private static final String URL = "jdbc:mysql://localhost:3306/db_access";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private java.sql.Date convertToDateViaLocalDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(String.valueOf(dateToConvert));
    }

    private void setParams(Email email, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, email.getRecipient());
        preparedStatement.setString(2, email.getSubject());
        preparedStatement.setString(3, email.getBody());
        preparedStatement.setDate(4, convertToDateViaLocalDate(email.getDate()));
    }

    @Override
    public void createEmail(Email email) throws IOException {

    }

    @Override
    public void save(Email email) {
        String sql = "INSERT INTO db_access (recipient, subject, body, date) VALUES (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(email, preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Email getById(int id) {
        Email email = null;
        String sql = "SELECT `recipient`, `subject`, `body`, `date` FROM db_access WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String recipient = resultSet.getString("recipient");
                String subject = resultSet.getString("subject");
                String body = resultSet.getString("body");
                LocalDate date = convertToLocalDateViaSqlDate(resultSet.getDate("date"));
                email = new Email(id, recipient, subject, body, date);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return email;
    }

    @Override
    public void save(List<Email> email) {
        String sql = "INSERT INTO db_access (recipient, subject, body, date) VALUES (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams((Email) email, preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<Email> findAllEmails() throws IOException {
        List<Email> list = new ArrayList<>();
        String sql = "SELECT `id`,`recipient`, `subject`, `body`, `date` FROM db_access";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String recipient = resultSet.getString("recipient");
                String subject = resultSet.getString("subject");
                String body = resultSet.getString("body");
                LocalDate localDate = convertToLocalDateViaSqlDate(resultSet.getDate("date"));
                list.add(new Email(id, recipient, subject, body, localDate));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM db_access WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void deleteById() {

    }

    @Override
    public List<Email> findEmailsForSending() throws IOException {
        LocalDate time = LocalDate.now();
        List<Email> emailList = findAllEmails();
        List<Email> result = new ArrayList<>();

        for (Email email : emailList) {
            if (time.equals(email.getDate())) {
                result.add(email);
            }
        }
        return result;
    }

    @Override
    public void deleteEmailByDate(LocalDateTime time) throws IOException {
        String sql = "DELETE FROM db_access WHERE date = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(4, convertToDateViaLocalDate(time.toLocalDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}