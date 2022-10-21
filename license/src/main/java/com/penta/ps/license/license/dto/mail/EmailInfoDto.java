package com.penta.ps.license.license.dto.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailInfoDto {
    @JsonProperty(value = "toAddress")
    private String toAddress;

    @JsonProperty(value = "fromAddress")
    private String fromAddress;

    @JsonProperty(value = "smtpAddress")
    private String smtpAddress;

    @JsonProperty(value = "smtpPort")
    private int smtpPort;

    @JsonProperty(value = "userName")
    private String userName;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "isUsingTls")
    private int isUsingTls;
}
