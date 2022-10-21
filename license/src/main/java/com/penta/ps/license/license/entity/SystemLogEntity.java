package com.penta.ps.license.license.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "system_log", schema = "log", catalog = "log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemLogEntity {

    @Id
    @Column
    private int id;

    @Column
    private int facility;

    @Column(name = "log_level")
    private int logLevel;

    @Column(length = 512)
    private String msg;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Builder
    public SystemLogEntity(int facility, int logLevel, String msg, Timestamp createTime) {
        this.facility = facility;
        this.logLevel = logLevel;
        this.msg = msg;
        this.createTime = createTime;
    }
}
