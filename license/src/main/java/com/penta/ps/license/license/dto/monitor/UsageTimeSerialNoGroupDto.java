package com.penta.ps.license.license.dto.monitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsageTimeSerialNoGroupDto {
    String serialNo;
    Long minuteOfUsageTime;

    @Builder
    public UsageTimeSerialNoGroupDto(String serialNo, Long minuteOfUsageTime) {
        this.serialNo = serialNo;
        this.minuteOfUsageTime = minuteOfUsageTime;
    }
}
