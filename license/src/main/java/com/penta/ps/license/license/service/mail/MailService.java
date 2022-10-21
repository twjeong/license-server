package com.penta.ps.license.license.service.mail;

import com.penta.ps.license.license.dto.mail.MailDto;
import com.penta.ps.license.license.entity.EmailInfo;
import com.penta.ps.license.license.repository.EmailInfoJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

@Service
public class MailService {

    private EmailInfoJpaRepo emailInfoJpaRepo;

    public boolean emailAlarmFlag;

    public MailService(EmailInfoJpaRepo emailInfoJpaRepo) throws IOException {

        this.emailInfoJpaRepo = emailInfoJpaRepo;

        ApplicationHome home = new ApplicationHome(this.getClass());
        File file = new File(
                home.getDir() + File.separator + "conf");
        file.mkdir();

        File confFile = new File(
                home.getDir() + File.separator + "conf" + File.separator + "setup.conf");

        boolean isExists = confFile.exists();
        if(!isExists) {
            confFile.createNewFile();

            OutputStream output = new FileOutputStream(
                    home.getDir() + File.separator + "conf" + File.separator+ "setup.conf");

            output.write("mail-alarm=false".getBytes());
            output.close();
        }

        BufferedReader br = new BufferedReader(new FileReader(confFile));
        String line;
        while((line = br.readLine()) != null){
            StringTokenizer s = new StringTokenizer(line);

            if (s.nextToken("=").equals("mail-alarm")) {
                if(s.nextElement().equals("true")) {
                    emailAlarmFlag = true;
                } else {
                    emailAlarmFlag = false;
                }
            } else if(s.nextToken("=").equals("log-alarm")) {
                if(s.nextElement().equals("true")) {
                    //logAlarmFlag = true;
                } else {
                    //logAlarmFlag = false;
                }
            }

        }
        br.close();
    }

    public void send(MailDto mailDto) {
        List<EmailInfo> mailInfo = emailInfoJpaRepo.findAllById();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Base64.Decoder decoder = Base64.getDecoder();

        for (int i = 0 ; i < mailInfo.size() ; i++) {
            mailSender.setHost(mailInfo.get(i).getSmtpAddress());
            mailSender.setPort(mailInfo.get(i).getSmtpPort());
            mailSender.setUsername(mailInfo.get(i).getUserName());

            byte[] passwordBytes = mailInfo.get(i).getPassword().getBytes();
            byte[] decodedBytes = decoder.decode(passwordBytes);

            mailSender.setPassword(new String(decodedBytes));

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            mailSender.setJavaMailProperties(props);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailInfo.get(i).getToAddress());
            message.setFrom(mailInfo.get(i).getFromAddress());
            message.setSubject(mailDto.getSubject());
            message.setText(mailDto.getMessage());

            if (emailAlarmFlag) {
                mailSender.send(message);
            }
        }
    }
}

