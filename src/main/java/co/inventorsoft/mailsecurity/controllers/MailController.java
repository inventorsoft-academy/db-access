package co.inventorsoft.mailsecurity.controllers;

import co.inventorsoft.mailsecurity.models.Email;
import co.inventorsoft.mailsecurity.repositories.EmailRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MailController {

    final
    EmailRepository emailRepository;

    public MailController(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMailPage(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        return "mail";
    }

    @GetMapping("/mail")
    @ResponseStatus(HttpStatus.OK)
    public String createMail(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        return "mail";
    }

    @PostMapping("/mail")
    @ResponseStatus(HttpStatus.OK)
    public String saveMail(@ModelAttribute("email") Email email) {
        emailRepository.saveMail(email);
        return "redirect:/";
    }

    @DeleteMapping("mail/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteMail(@PathVariable int id) {
        emailRepository.delete(emailRepository.findById(id));
        return "redirect:/";
    }
}
