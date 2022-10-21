package com.penta.ps.license.license.dto.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DailySummaryInfoDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty(value = "date")
    LocalDate date;

    @JsonProperty(value = "minuteOfUsageTime")
    Long minuteOfUsageTime;

    @Builder
    public DailySummaryInfoDto(LocalDate date, Long minuteOfUsageTime) {
        this.date = date;
        this.minuteOfUsageTime = minuteOfUsageTime;
    }
}
