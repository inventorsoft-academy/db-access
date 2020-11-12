package com.hometask.task_security.controller;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.repository.db_repo.JDBCRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {

    private final JDBCRepoImpl repo;

    @Autowired
    public EmailController(JDBCRepoImpl repo) {
        this.repo = repo;
    }


    @GetMapping("/index")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/mails")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    public String showAll(Model model) throws IOException {
        model.addAttribute("emails", repo.findAllEmails());
        return "emails";
    }

    @GetMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.OK)
    public String createEmail(@ModelAttribute("email") Email email){
        return "newEmail";
    }

    @PostMapping("/newEmail")
    @PreAuthorize("hasAnyAuthority('edit')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEmail(@ModelAttribute("email") Email email) throws IOException {
        repo.createEmail(email);
        return "redirect:/index";
    }

    @DeleteMapping("/delete/email/{time}")
    @PreAuthorize("hasAnyAuthority('read','delete')")
    public void deleteEmailByDate(@PathVariable LocalDateTime time) throws IOException {
        repo.deleteEmailByDate(time);
    }

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }
}