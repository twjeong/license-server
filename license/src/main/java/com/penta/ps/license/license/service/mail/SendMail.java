package com.penta.ps.license.license.service.mail;

import com.penta.ps.license.license.config.EmailConfig;
import com.penta.ps.license.license.dto.mail.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMail {
    private final EmailConfig emailConfig;

    public void sendMail(MailDto mailDto){
        JavaMailSenderImpl javaMailSender = emailConfig.setMailConfig();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(javaMailSender.getJavaMailProperties().getProperty(EmailConfig.MAIL_FROM_ADDRESS));
        simpleMailMessage.setTo(javaMailSender.getJavaMailProperties().getProperty(EmailConfig.MAIL_TO_ADDRESS));
        simpleMailMessage.setSubject(mailDto.getSubject());
        simpleMailMessage.setText(mailDto.getMessage());

        // smtp 주소 잘못 쓰면 exception 발생, 메일 정보 없으면 exception 발생, 여러가지 exception 발생 가능성 있음
        javaMailSender.send(simpleMailMessage);
    }
}
