package co.inventorsoft.jdbc.controller;

import co.inventorsoft.jdbc.model.Mail;
import co.inventorsoft.jdbc.model.MailRequest;
import co.inventorsoft.jdbc.service.MailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MailController {

    private MailServiceImpl mailService;

    public MailController(MailServiceImpl mailService){
        this.mailService=mailService;
    }

    @GetMapping("/mail")
    public ResponseEntity<Mail> getMail(
            @RequestParam long id){
        return ResponseEntity.of(Optional.of(
                mailService.read(id)));
    }

    @GetMapping("/mails")
    public ResponseEntity getMailList(){
        return ResponseEntity.of(Optional.of(
                mailService.readList()
        ));
    }

    @PostMapping("/mails")
    public ResponseEntity createMail(
            @RequestBody MailRequest mail){
        Mail newMail = new Mail(mail.getId(),mail.getRecipient(),mail.getSubject(),mail.getBody());
        mailService.create(newMail);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMail);
    }

    @DeleteMapping("/mails")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMail(
            @RequestParam long id){
        mailService.delete(id);
    }

}
