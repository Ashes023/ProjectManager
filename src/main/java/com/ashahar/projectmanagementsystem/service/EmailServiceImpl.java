package com.ashahar.projectmanagementsystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private JavaMailSender mailSender;

    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Join Project Team Invitation";
        String text = "Click the link to join the project team: " + link;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true);
        mimeMessageHelper.setTo(userEmail);

        try{
            mailSender.send(mimeMessage);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
