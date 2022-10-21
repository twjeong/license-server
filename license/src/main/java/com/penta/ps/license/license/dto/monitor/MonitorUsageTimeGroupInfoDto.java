package com.penta.ps.license.license.dto.monitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MonitorUsageTimeGroupInfoDto {
    private String custName;
    private List<String> serialNo;

    @Builder
    public MonitorUsageTimeGroupInfoDto(String custName, List<String> serialNo) {
        this.custName = custName;
        this.serialNo = serialNo;
    }
}
