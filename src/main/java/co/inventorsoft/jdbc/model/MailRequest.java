package co.inventorsoft.jdbc.model;

import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class MailRequest {
    private long id;
    private String recipient;
    private String subject;
    private String body;

    @Override
    public String toString() {
        return "MailRequest{" +
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
        MailRequest that = (MailRequest) o;
        return id == that.id &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
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

    public MailRequest(long id, String recipient, String subject, String body) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }
}
