package com.penta.ps.license.license.dto.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllDateSummaryInfoDto {
    @JsonProperty(value = "minuteOfUsageTime")
    Long minuteOfUsageTime;

    @Builder
    public AllDateSummaryInfoDto(Long minuteOfUsageTime) {
        this.minuteOfUsageTime = minuteOfUsageTime;
    }
}
