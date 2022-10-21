package com.penta.ps.license.license.dto.log;

import com.penta.ps.license.license.entity.PolicyLogEntity;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import lombok.*;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PolicyLogDto {
    private ManagerType facility;
    private LogLevelType logLevel;
    private String msg;
    private Timestamp createTime;

    @Builder
    public PolicyLogDto(ManagerType facility,
                        LogLevelType logLevel,
                        String msg,
                        Timestamp createTime) {
        this.facility = facility;
        this.logLevel = logLevel;
        this.msg = msg;
        this.createTime = createTime;
    }

    public PolicyLogEntity toEntity() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        PolicyLogEntity policyLogEntity = PolicyLogEntity.builder()
                .facility(facility.ordinal())
                .logLevel(logLevel.ordinal())
                .msg(msg)
                .createTime(Timestamp.valueOf(zdt.toLocalDateTime()))
                .build();

        return policyLogEntity;
    }

}

