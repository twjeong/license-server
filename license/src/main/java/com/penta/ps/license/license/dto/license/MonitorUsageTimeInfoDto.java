package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MonitorUsageTimeInfoDto {
    private String serialNo;

    private String date;
    private String custNm;
    private Integer minuteOfUsageTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifyTime;

    @Builder
    public MonitorUsageTimeInfoDto(String serialNo,
                                   String date,
                                   String custNm,
                                   Integer minuteOfUsageTime,
                                   LocalDateTime createTime,
                                   LocalDateTime modifyTime) {
        this.serialNo = serialNo;
        this.date = date;
        this.custNm = custNm;
        this.minuteOfUsageTime = minuteOfUsageTime;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }
}
