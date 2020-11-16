package co.inventorsoft.jdbc.controller;

import co.inventorsoft.jdbc.model.Mail;
import co.inventorsoft.jdbc.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public String mainPage() {
        return "index";
    }

    @GetMapping("/mails")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.OK)
    public String showAllEmails(Model model) {
        model.addAttribute("emails", mailService.findAll());
        return "mails";
    }

    @GetMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.OK)
    public String createEmail(@ModelAttribute("email") Mail mail) {
        return "newEmail";
    }

    @PostMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEmail(@ModelAttribute("email") Mail mail) {
        mailService.saveEmail(mail);
        return "redirect:/index";
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/mails/{id}")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.OK)
    public String getEmailById(@PathVariable Long id, Model model) {
        model.addAttribute("email", mailService.findById(id));
        return "emailById";
    }

    @DeleteMapping("/delete/email/{id}")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteByEmailById(@PathVariable Long id) {
        mailService.deleteById(id);
        return "redirect:/index";
    }

}
