package com.example.demo.repository.jdbc;

import com.example.demo.model.dto.MessageDto;
import com.example.demo.model.entity.Message;
import com.example.demo.model.enums.Status;
import com.example.demo.model.enums.jdbc.JdbcConnect;
import com.example.demo.model.enums.sql.SqlRequestMessages;
import com.example.demo.service.MessageService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcConnection implements MessageService {

    private Connection dbConnection;

    @SneakyThrows
    private Connection getDbConnection() {
        Class.forName(JdbcConnect.POSTGRESQL_DRIVER.getData());
        this.dbConnection = DriverManager.getConnection(JdbcConnect.CONNECTION_URL.getData(), JdbcConnect.USER_NAME.getData(), JdbcConnect.PASSWORD.getData());
        this.dbConnection.setAutoCommit(false);
        return this.dbConnection;
    }

    @Override
    @SneakyThrows
    public void save(Message message) {
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_INSERT.getRequest());
        statement.setString(1, message.getSubject());
        statement.setString(2, message.getEmail_to());
        statement.setString(3, message.getEmail_text());
        statement.setLong(4, message.getFuture_second());
        statement.setLong(5, new Date().getTime());
        statement.setString(6, String.valueOf(message.getStatus()));
        statement.executeUpdate();
        transaction(statement);
    }

    @Override
    @SneakyThrows
    public List<Message> getAllMessage() {
        List<Message> messages = new ArrayList();
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_SELECT_ALL.getRequest());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Message message = new Message();
            message.setId(resultSet.getLong("id"));
            message.setSubject(resultSet.getString("subject"));
            message.setEmail_to(resultSet.getString("email_to"));
            message.setEmail_text(resultSet.getString("email_text"));
            message.setFuture_second(resultSet.getLong("futureSecond"));
            message.setCurrentTime(resultSet.getLong("currentTime"));
            message.setStatus(Status.valueOf(resultSet.getString("status")));
            messages.add(message);
        }

        transaction(statement);
        return messages;
    }

    @Override
    @SneakyThrows
    public List<MessageDto> getAllMessageDto() {
        List<MessageDto> messages = new ArrayList();
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_SELECT_ALL.getRequest());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            MessageDto message = new MessageDto();
            message.setSubject(resultSet.getString("subject"));
            message.setEmail_to(resultSet.getString("email_to"));
            message.setEmail_text(resultSet.getString("email_text"));
            message.setStatus(Status.valueOf(resultSet.getString("status")));
            messages.add(message);
        }

        transaction(statement);
        return messages;
    }

    @Override
    @SneakyThrows
    public Message findById(Long id) {
        Message message = new Message();
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_SELECT_MESSAGE_ID.getRequest());
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            message.setId(resultSet.getLong("id"));
            message.setSubject(resultSet.getString("subject"));
            message.setEmail_to(resultSet.getString("email_to"));
            message.setEmail_text(resultSet.getString("email_text"));
            message.setFuture_second(resultSet.getLong("futureSecond"));
            message.setCurrentTime(resultSet.getLong("currentTime"));
            message.setStatus(Status.valueOf(resultSet.getString("status")));
        }

        transaction(statement);
        return message;

    }

    @SneakyThrows
    public void updateTimeById(long id, long second) {
        PreparedStatement preparedStatement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_UPDATE_TIME.getRequest());
        preparedStatement.setLong(1, second);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
        transaction(preparedStatement);
    }

    @Override
    @SneakyThrows
    public void updateStatusById(long id, String status) {
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_UPDATE_STATUS.getRequest());
        statement.setString(1, status);
        statement.setLong(2, id);
        statement.executeUpdate();
        transaction(statement);
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_DELETE_MESSAGE_ID.getRequest());
        statement.setLong(1, id);
        statement.execute();
        transaction(statement);
    }

    private void transaction(PreparedStatement statement) throws SQLException {
        try {
            statement.getConnection().commit();
        } catch (SQLException var6) {
            if (!statement.getConnection().isClosed()) {
                statement.getConnection().rollback();
            }
        } finally {
            statement.getConnection().close();
        }

    }
}
