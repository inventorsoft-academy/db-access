package com.example.demo.model.enums.sql;

public enum SqlRequestMessages {
    SQL_INSERT("INSERT INTO messages(subject, email_to, email_text, futureSecond, currentTime, status) VALUES (?,?,?,?,?,?)"),
    SQL_SELECT_ALL("SELECT id, subject, email_to, email_text, futureSecond, currentTime, status FROM messages"),
    SQL_UPDATE_TIME("update messages set futureSecond=? where id=?"),
    SQL_UPDATE_STATUS("UPDATE messages SET status=? WHERE id=?"),
    SQL_SELECT_MESSAGE_ID("SELECT id, subject, email_to, email_text, futureSecond, currentTime, status FROM messages WHERE id=?"),
    SQL_DELETE_MESSAGE_ID("DELETE FROM messages WHERE id=?");

    private String request;

    private SqlRequestMessages(String request) {
        this.request = request;
    }

    public String getRequest() {
        return this.request;
    }
}

