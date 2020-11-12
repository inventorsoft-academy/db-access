package com.hometask.task_security.repository;


import com.hometask.task_security.model.Email;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

    public interface EmailRepo {

        void createEmail(Email email) throws IOException;

        void save(Email email) throws IOException;

        public void save(List<Email> email) throws IOException;

        List<Email> findAllEmails() throws IOException;

        List<Email> findEmailsForSending() throws IOException;

        void deleteEmailByDate(LocalDateTime time) throws IOException;

        void deleteById(int id);

        void deleteById();

         Email getById(int id);
    }

