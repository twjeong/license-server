package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.monitor.MonitorUsageTimeGroupInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMonitorUsageTimeGroupInfo {
    List<MonitorUsageTimeGroupInfoDto> usageTimeInfoGroupList;

    @Builder
    public ResponseMonitorUsageTimeGroupInfo(List<MonitorUsageTimeGroupInfoDto> usageTimeInfoGroupList) {
        this.usageTimeInfoGroupList = usageTimeInfoGroupList;
    }
}
