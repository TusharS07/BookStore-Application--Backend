package com.example.bookstoreapplication.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    JavaMailSender javaMail;

    @Value(value = "${spring.mail.username}")
    private String sender;

    public String sendMail(String email, String mailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(mailBody);
        javaMail.send(simpleMailMessage);
        return "mail send successfully";
    }
}
