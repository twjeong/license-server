package com.penta.ps.license.license.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "email_info", schema = "email", catalog = "email")
public class EmailInfo {
    @Column(name = "to_address", nullable = false, length = 128)
    private String toAddress;

    @Column(name = "from_address", nullable = false, length = 128)
    private String fromAddress;

    @Column(name = "smtp_address", nullable = false, length = 128)
    private String smtpAddress;

    @Column(name = "smtp_port", nullable = false, columnDefinition = "smallint")
    private int smtpPort;

    @Id
    @Column(name = "username", nullable = false, length = 128)
    private String userName;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "tls", nullable = false, columnDefinition = "smallint")
    private int isUsingTls;
}
