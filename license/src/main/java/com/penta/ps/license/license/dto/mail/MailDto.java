package com.penta.ps.license.license.dto.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String subject;
    private String message;

    @Builder
    public MailDto(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }
}
