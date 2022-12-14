package com.sms.project.chatsystem.service;

import com.sms.project.chatsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    private String OTP_NUMPER;

    public void sendEmailMessage(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        OTPGenerator();
        simpleMailMessage.setFrom("smschatbox@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("OTP");
        simpleMailMessage.setText("confirmation OTP " + OTP_NUMPER);

        javaMailSender.send(simpleMailMessage);
    }

    private String OTPGenerator() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    public String getOTP_NUMPER() {
        return OTP_NUMPER;
    }
}
