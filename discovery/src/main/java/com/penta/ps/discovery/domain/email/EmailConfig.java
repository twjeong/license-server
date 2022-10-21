package com.penta.ps.discovery.domain.email;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

@Configuration
@AllArgsConstructor
public class EmailConfig {
    private static final String MAIL_DEBUG = "mail.debug";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_TO_ADDRESS = "mail.toaddress";
    public static final String MAIL_FROM_ADDRESS = "mail.fromaddress";

    private final EmailRepository emailInfoJpaRepo;

    public JavaMailSenderImpl setMailConfig() {
        List<EmailEntity> list = emailInfoJpaRepo.findAll();
        EmailEntity emailInfo = list.get(0);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailInfo.getSmtpAddress());
        javaMailSender.setProtocol("smtp"); // smtp 프로토콜만 지원
        javaMailSender.setPort(emailInfo.getSmtpPort());
        javaMailSender.setUsername(emailInfo.getUserName());

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] passwordBytes = emailInfo.getPassword().getBytes();
        byte[] decodedBytes = decoder.decode(passwordBytes);

        javaMailSender.setPassword(new String(decodedBytes));
        javaMailSender.setDefaultEncoding("UTF-8"); // 인코딩 utf-8 만 지원
        Properties properties = javaMailSender.getJavaMailProperties();

        if(emailInfo.getIsUsingTls() == 1){
            properties.put(MAIL_SMTP_STARTTLS_REQUIRED, true);
            properties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
            properties.put(MAIL_SMTP_AUTH, true);
        }
        properties.put(MAIL_DEBUG, true);
        properties.put(MAIL_TO_ADDRESS, emailInfo.getToAddress());
        properties.put(MAIL_FROM_ADDRESS, emailInfo.getFromAddress());

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
