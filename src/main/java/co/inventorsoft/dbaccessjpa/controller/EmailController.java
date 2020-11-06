package co.inventorsoft.dbaccessjpa.controller;

import co.inventorsoft.dbaccessjpa.model.Email;
import co.inventorsoft.dbaccessjpa.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMailPage() {
        return "redirect:/emails";
    }

    @GetMapping("/emails")
    @ResponseStatus(HttpStatus.OK)
    public String findAll(Model model){

        List<Email> emails = emailService.findAll();
        model.addAttribute("emails", emails);

        return "email-list";
    }

    @GetMapping("/email-create")
    @ResponseStatus(HttpStatus.OK)
    public String createEmailForm(@ModelAttribute("email") Email email){
        return "email-create";
    }

    @PostMapping("/email-create")
    @ResponseStatus(HttpStatus.FOUND)
    public String createEmail(@ModelAttribute("email") Email email){
        emailService.saveEmail(email);
        return "redirect:/emails";
    }

    @GetMapping("/email-delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteEmail(@PathVariable("id") Long id) {
        emailService.deleteById(id);
        return "redirect:/emails";
    }

    @GetMapping("/email-update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        Email email = emailService.findById(id);
        model.addAttribute("email", email);
        return "email-update";
    }

    @PostMapping("/email-update")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateEmail(@ModelAttribute("email") Email email){
        emailService.saveEmail(email);
        return "redirect:/emails";
    }

}
