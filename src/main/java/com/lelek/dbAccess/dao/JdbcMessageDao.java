package com.lelek.dbAccess.dao;

import com.lelek.dbAccess.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class JdbcMessageDao implements MessageDao {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/messageDb";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    @Override
    public List<MessageDto> getMessages() {
        List<MessageDto> dtoList = new ArrayList<>();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT message_id, sent, recipient, subject, text, send_date " +
                            "FROM mail_message");
            while (resultSet.next()) {
                dtoList.add(new MessageDto(resultSet.getLong("message_id"),
                        resultSet.getBoolean("sent"),
                        resultSet.getString("recipient"),
                        resultSet.getString("subject"),
                        resultSet.getString("text"),
                        resultSet.getString("send_date")));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("error " + e);
        }
        return dtoList;
    }

    @Override
    public void saveMessage(MessageDto messageDto) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO mail_message (sent, recipient, subject, text, send_date) " +
                            "VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setBoolean(1, messageDto.isSent());
            preparedStatement.setString(2, messageDto.getTo());
            preparedStatement.setString(3, messageDto.getSubject());
            preparedStatement.setString(4, messageDto.getText());
            preparedStatement.setString(5, messageDto.getDate());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("error " + e);
        }
    }

    @Override
    public MessageDto getMessage(long id) {
        MessageDto messageDto = null;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT message_id, sent, recipient, subject, text, send_date " +
                            "FROM mail_message WHERE message_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            messageDto = new MessageDto(resultSet.getLong("message_id"),
                    resultSet.getBoolean("sent"),
                    resultSet.getString("recipient"),
                    resultSet.getString("subject"),
                    resultSet.getString("text"),
                    resultSet.getString("send_date"));
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("error " + e);
        }
        return messageDto;
    }

    @Override
    public void removeMessage(long id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM mail_message WHERE message_id = ?;");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("error " + e);
        }
    }

    @Override
    public void updateMessage(long id, MessageDto updates) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE mail_message SET sent = ?, recipient = ?, subject = ?, " +
                            "text = ?, send_date = ? WHERE message_id = ?;");
            preparedStatement.setBoolean(1, updates.isSent());
            preparedStatement.setString(2, updates.getTo());
            preparedStatement.setString(3, updates.getSubject());
            preparedStatement.setString(4, updates.getText());
            preparedStatement.setString(5, updates.getDate());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("error " + e);
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            log.error("error " + e);
        }
        return connection;
    }
}
