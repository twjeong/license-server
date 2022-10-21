package com.penta.ps.discovery.domain.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfig emailConfig;

    public void sendMail(String subject, String message){
        JavaMailSenderImpl javaMailSender = emailConfig.setMailConfig();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(javaMailSender.getJavaMailProperties().getProperty(EmailConfig.MAIL_FROM_ADDRESS));
        simpleMailMessage.setTo(javaMailSender.getJavaMailProperties().getProperty(EmailConfig.MAIL_TO_ADDRESS));
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        // smtp 주소 잘못 쓰면 exception 발생, 메일 정보 없으면 exception 발생, 여러가지 exception 발생 가능성 있음
        javaMailSender.send(simpleMailMessage);
    }
}
