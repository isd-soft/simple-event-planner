package com.internship.sep.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String text, String hostEmail) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@sep.com");
            message.setTo(hostEmail);
            message.setSubject("Simple Event Planner");
            message.setText(text);
            emailSender.send(message);

    }
}
