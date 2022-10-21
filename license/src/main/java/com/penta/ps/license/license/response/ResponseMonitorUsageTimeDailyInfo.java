package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.monitor.MonitorUsageTimeDailyInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMonitorUsageTimeDailyInfo {
    List<MonitorUsageTimeDailyInfoDto> monitorUsageTimeDailyInfoDtos;

    @Builder
    public ResponseMonitorUsageTimeDailyInfo(List<MonitorUsageTimeDailyInfoDto> monitorUsageTimeDailyInfoDtos) {
        this.monitorUsageTimeDailyInfoDtos = monitorUsageTimeDailyInfoDtos;
    }
}
