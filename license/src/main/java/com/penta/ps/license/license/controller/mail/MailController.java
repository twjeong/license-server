package com.penta.ps.license.license.controller.mail;

import com.penta.ps.license.license.entity.EmailInfo;
import com.penta.ps.license.license.repository.EmailInfoJpaRepo;
import com.penta.ps.license.license.response.ResponseMessage;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.mail.MailService;
import com.penta.ps.license.license.type.LogLevelType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final EmailInfoJpaRepo emailInfoJpaRepo;
    private final MailService mailService;
    private final LogModuleService logModuleService;

    @GetMapping(value = "/setup/email/alarmFlag", produces = "application/json")
    public boolean emailAlarmFlag() {
        return mailService.emailAlarmFlag;
    }

    @PutMapping("/setup/email/alarmFlag")
    public ResponseEntity<ResponseMessage> setupEmailAlarmFlag(@RequestBody Map<String, Object> param) throws IOException {
        String line = null;
        String repLine = null;

        String flag = param.get("flag").toString();

        ApplicationHome home = new ApplicationHome(this.getClass());
        File file = new File(
                home.getDir() + File.separator + "conf");
        file.mkdir();

        File confFile = new File(
                home.getDir() + File.separator + "conf" + File.separator + "setup.conf");

        BufferedReader br = new BufferedReader(new FileReader(confFile));

        while((line = br.readLine()) != null) {
            StringTokenizer s = new StringTokenizer(line);

            if (s.nextToken("=").equals("mail-alarm")) {
                if (flag.equals("true")) {
                    repLine = line.replaceAll("mail-alarm=false", "mail-alarm=true");
                    mailService.emailAlarmFlag = true;
                } else {
                    OutputStream output = new FileOutputStream(
                            home.getDir() + File.separator + "conf" + File.separator + "setup.conf");
                    repLine = line.replaceAll("mail-alarm=true", "mail-alarm=false");
                    mailService.emailAlarmFlag = false;
                }
            } else {
                repLine = repLine + "\n" + line;
            }

        }
        br.close();

        OutputStream output = new FileOutputStream(
                home.getDir() + File.separator + "conf" + File.separator + "setup.conf");
        output.write(repLine.getBytes());
        output.close();

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @GetMapping(value = "/setup/email", produces = "application/json")
    public EmailInfo setupEmail() {
        List<EmailInfo> mailInfo = emailInfoJpaRepo.findAllById();

        if (mailInfo.isEmpty()) {
            return null;
        }

        return mailInfo.get(0);
    }

    @PostMapping("/setup/email")
    public ResponseEntity<ResponseMessage> setupEmail(@RequestBody Map<String, Object> param) throws SSLException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {

        String smtpAddress = param.get("smtpAddress").toString();
        int smtpPort = Integer.parseInt(param.get("smtpPort").toString());
        String toAddress = param.get("toAddress").toString();
        String fromAddress = param.get("fromAddress").toString();
        String userName = param.get("username").toString();
        String password = param.get("password").toString();
        int isUsingTls = Integer.parseInt(param.get("isUsingTls").toString());

        emailInfoJpaRepo.deleteAll();

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] targetBytes = password.getBytes();
        byte[] encodeBytes = encoder.encode(targetBytes);
        String encodePassword = new String(encodeBytes);

        EmailInfo mailInfo = EmailInfo.builder()
                .toAddress(toAddress)
                .fromAddress(fromAddress)
                .smtpAddress(smtpAddress)
                .smtpPort(smtpPort)
                .userName(userName)
                .password(encodePassword)
                .isUsingTls(isUsingTls)
                .build();

        emailInfoJpaRepo.save(mailInfo);

        logModuleService.SaveEventLog(LogLevelType.INFO, "[Setup] E-Mail information edited.");

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @DeleteMapping("/setup/email")
    public ResponseEntity<ResponseMessage> deleteEmail() throws SSLException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        emailInfoJpaRepo.deleteAll();

        logModuleService.SaveEventLog(LogLevelType.INFO, "[Setup] E-Mail information deleted.");
        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

}
