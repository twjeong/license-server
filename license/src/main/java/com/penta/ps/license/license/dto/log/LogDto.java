package com.penta.ps.license.license.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class LogDto {
    private ManagerType facility;
    private LogLevelType logLevel;
    private String msg;

    @JsonFormat(timezone = "Asia/Seoul")
    private Timestamp createTime;

    @Builder
    public LogDto(ManagerType facility, LogLevelType logLevel, String msg, Timestamp createTime) {
        if(facility.equals(null)){
            facility = ManagerType.CONTRACT;
        }

        if(logLevel.equals(null)){
            logLevel = LogLevelType.INFO;
        }

        this.facility = facility;
        this.logLevel = logLevel;
        this.msg = msg;
        this.createTime = createTime;
    }
}
