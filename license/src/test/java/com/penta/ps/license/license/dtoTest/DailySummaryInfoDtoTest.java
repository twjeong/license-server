package com.penta.ps.license.license.dtoTest;

import com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailySummaryInfoDtoTest {

    @Test
    void getDate() {
        final DailySummaryInfoDto dailySummaryInfoDto =
                DailySummaryInfoDto.builder().date(LocalDate.now()).build();

        assertEquals(LocalDate.now(), dailySummaryInfoDto.getDate());
    }

    @Test
    void getMinuteOfUsageTime() {
        final DailySummaryInfoDto dailySummaryInfoDto =
                DailySummaryInfoDto.builder().minuteOfUsageTime((long) 100).build();

        assertEquals(100, dailySummaryInfoDto.getMinuteOfUsageTime());
    }

}