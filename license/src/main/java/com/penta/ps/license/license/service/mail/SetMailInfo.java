package com.penta.ps.license.license.service.mail;

import com.penta.ps.license.license.dto.mail.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetMailInfo {
    private final MailDto mailDto = new MailDto();

    private final String subject = "[DAmo, Penta Security Systems, Inc.] Notification for service down";

    private final String filstMessage = "Penta Security Systems Inc. provides notification for service down issue.\n" +
            "We would like to notify you about a problem with the ";

    private final String secondMessage = "You must take action to get rid of risk of service.";

    public MailDto setMailInfo(String moduleName){
        mailDto.setSubject(subject);
        mailDto.setMessage(filstMessage + moduleName + ".\n" + secondMessage);

        return mailDto;
    }
}
