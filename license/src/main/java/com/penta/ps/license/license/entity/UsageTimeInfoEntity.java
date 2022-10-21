package com.penta.ps.license.license.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@IdClass(UsageTimeInfoEntityPrimaryKey.class)
@Table(name = "usage_time_info", schema = "monitor", catalog = "monitor")
public class UsageTimeInfoEntity {
    @Id
    @Column(name = "serial_no", length = 128, nullable = false)
    private String serialNo;

    @Id
    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "cust_nm", length = 128)
    private String custNm;

    @Column(name = "minute_of_usage_time")
    private Integer minuteOfUsageTime;

    @Column(name = "create_time", updatable = false)
//	@CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "modify_time")
//	@UpdateTimestamp
    private LocalDateTime modifyTime;

    @Builder
    public UsageTimeInfoEntity(String serialNo, LocalDate date, String custNm, Integer minuteOfUsageTime,
                               LocalDateTime createTime, LocalDateTime modifyTime) {
        this.serialNo = serialNo;
        this.date = date;
        this.custNm = custNm;
        this.minuteOfUsageTime = minuteOfUsageTime;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }
}
