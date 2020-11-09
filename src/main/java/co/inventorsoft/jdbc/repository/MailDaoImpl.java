package co.inventorsoft.jdbc.repository;

import co.inventorsoft.jdbc.configuration.Driver;
import co.inventorsoft.jdbc.model.Mail;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
@Repository
public class MailDaoImpl implements MailDao {

     private static final String TABLE_MAIL_COLUMN_ID = "id";
     private static final String TABLE_MAIL_COLUMN_RECIPIENT = "recipient";
     private static final String TABLE_MAIL_COLUMN_SUBJECT = "subject";
     private static final String TABLE_MAIL_COLUMN_BODY = "body";
     private static final String TABLE_NAME_MAIL = "mail";
    private ResultSet resultSet;

    @Override
    public List<Mail> readList() {
        List<Mail> mails = new LinkedList<>();
        try {
            String sql = "SELECT " + TABLE_MAIL_COLUMN_ID + ", " + TABLE_MAIL_COLUMN_RECIPIENT + ", " +
                    TABLE_MAIL_COLUMN_SUBJECT + ", " + TABLE_MAIL_COLUMN_BODY + " FROM [" + TABLE_NAME_MAIL + "]";
            PreparedStatement preparedStatement = Driver.getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.getMetaData() != null) {
                while (resultSet.next()) {
                    long id = resultSet.getInt(TABLE_MAIL_COLUMN_ID);
                    String recipient = resultSet.getString(TABLE_MAIL_COLUMN_RECIPIENT);
                    String subject = resultSet.getString(TABLE_MAIL_COLUMN_SUBJECT);
                    String body = resultSet.getString(TABLE_MAIL_COLUMN_BODY);
                    mails.add(new Mail(id, recipient, subject,body));
                }
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Wrong column " + " or table");
        }
        return mails;
    }

    @Override
    public void create(Mail mail) {
        String sql = "INSERT INTO [" + TABLE_NAME_MAIL + "] (" + TABLE_MAIL_COLUMN_RECIPIENT + ", " +
                TABLE_MAIL_COLUMN_SUBJECT + ", " + TABLE_MAIL_COLUMN_BODY + ") VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = Driver.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,mail.getRecipient());
            preparedStatement.setString(2, mail.getSubject());
            preparedStatement.setString(3,mail.getBody());
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            System.out.println("Mail creation failed");
        }
    }

    @Override
    public Mail read(long id) {
        Mail mail = null;
        try {
            String sql = "SELECT " + TABLE_MAIL_COLUMN_ID + ", " + TABLE_MAIL_COLUMN_RECIPIENT + ", "
                    + TABLE_MAIL_COLUMN_SUBJECT + ", " + TABLE_MAIL_COLUMN_BODY
                    + " FROM [" + TABLE_NAME_MAIL + "] WHERE " + TABLE_MAIL_COLUMN_ID +"=?";
            PreparedStatement preparedStatement = Driver.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.getMetaData() != null) {
                while (resultSet.next()) {
                    long mailId = resultSet.getInt(TABLE_MAIL_COLUMN_ID);
                    String recipient = resultSet.getString(TABLE_MAIL_COLUMN_RECIPIENT);
                    String subject = resultSet.getString(TABLE_MAIL_COLUMN_SUBJECT);
                    String body = resultSet.getString(TABLE_MAIL_COLUMN_BODY);
                    mail = new Mail(mailId, recipient, subject,body);
                }
            }
            return mail;
        } catch (SQLException | IOException ex) {
            System.out.println("Wrong column " + TABLE_MAIL_COLUMN_ID + " or table");
            return null;
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM [" + TABLE_NAME_MAIL + "] WHERE " + TABLE_MAIL_COLUMN_ID + " =  ?";
        try {
            PreparedStatement preparedStatement = Driver.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            System.out.println("Deleting " + " is failed");
        }
    }
}
