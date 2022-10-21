package com.penta.ps.license.license.config;

// Gmail smtp 사용할 경우 보안 수준이 낮은 앱 설정을 사용해야 한다. (아니면 구글에서 차단함, 다른 곳들도 비슷할 듯)
// https://www.google.com/settings/security/lesssecureapps 접속해서 사용으로 선택 (구글 smtp의 경우)
// https://wehagohelp.zendesk.com/hc/ko/articles/360002345353-Gmail-%EC%97%B0%EB%8F%99%EC%9D%B4-%EC%95%88%EB%90%98%EB%8A%94-%EA%B2%BD%EC%9A%B0 참고용

import com.penta.ps.license.license.entity.EmailInfo;
import com.penta.ps.license.license.repository.EmailInfoJpaRepo;
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

    private final EmailInfoJpaRepo emailInfoJpaRepo;

    public JavaMailSenderImpl setMailConfig() {
        List<EmailInfo> list = emailInfoJpaRepo.findAll();
        EmailInfo emailInfo = list.get(0);

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

