package com.penta.ps.discovery.config;

import com.penta.ps.discovery.domain.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InstanceCanceledListener implements ApplicationListener<EurekaInstanceCanceledEvent> {
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(EurekaInstanceCanceledEvent event) {

        final String subject = "[DAmo, Penta Security Systems, Inc.] Notification for service down";

        final String firstMessage = "Penta Security Systems Inc. provides notification for service down issue.\n" +
                "We would like to notify you about a problem with the ";

        final String secondMessage = "You must take action to get rid of risk of service.";

        if(!event.isReplication()){
            event.setReplication(true);

            String message = firstMessage + event.getAppName() + ".\n" + secondMessage;

            emailService.sendMail(subject, message);
        }
    }
}
