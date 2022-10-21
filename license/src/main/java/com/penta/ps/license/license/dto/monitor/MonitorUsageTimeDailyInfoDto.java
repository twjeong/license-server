package com.penta.ps.license.license.dto.monitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonitorUsageTimeDailyInfoDto {
    private String date;
    private Integer minuteOfUsageTime;

    @Builder
    public MonitorUsageTimeDailyInfoDto(String date, Integer minuteOfUsageTime) {
        this.date = date;
        this.minuteOfUsageTime = minuteOfUsageTime;
    }
}
