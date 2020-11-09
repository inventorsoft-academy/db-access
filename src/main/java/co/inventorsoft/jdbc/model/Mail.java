package co.inventorsoft.jdbc.model;

import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class Mail {
    private long id;
    private String recipient;
    private String subject;
    private String body;

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return id == mail.id &&
                Objects.equals(recipient, mail.recipient) &&
                Objects.equals(subject, mail.subject) &&
                Objects.equals(body, mail.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipient, subject, body);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Mail(long id, String recipient, String subject, String body) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }
}
