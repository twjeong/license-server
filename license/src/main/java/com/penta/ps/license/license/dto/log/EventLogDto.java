package com.penta.ps.license.license.dto.log;

import com.penta.ps.license.license.entity.EventLogEntity;
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
public class EventLogDto {
    private ManagerType facility;
    private LogLevelType logLevel;
    private String msg;
    private Timestamp createTime;

    @Builder
    public EventLogDto(ManagerType facility,
                       LogLevelType logLevel,
                       String msg,
                       Timestamp createTime) {
        this.facility = facility;
        this.logLevel = logLevel;
        this.msg = msg;
        this.createTime = createTime;
    }

    public EventLogEntity toEntity() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        EventLogEntity eventLogEntity = EventLogEntity.builder()
                .facility(facility.ordinal())
                .logLevel(logLevel.ordinal())
                .msg(msg)
                .createTime(Timestamp.valueOf(zdt.toLocalDateTime()))
                .build();

        return eventLogEntity;
    }
}
