package co.inventorsoft.jdbc.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "mail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mail {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(unique = true, nullable = false)
     long id;

     @Column(nullable = false)
     String recipient;

     @Column(nullable = false)
     String subject;

     @Column(nullable = false)
     String body;

}
