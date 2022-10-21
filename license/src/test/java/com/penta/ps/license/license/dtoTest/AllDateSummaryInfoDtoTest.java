package com.penta.ps.license.license.dtoTest;

import com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllDateSummaryInfoDtoTest {

    @Test
    public void getMinuteOfUsageTime() {
        final AllDateSummaryInfoDto allDateSummaryInfoDto =
                AllDateSummaryInfoDto.builder().minuteOfUsageTime((long) 100).build();
        final Long minuteOfUsageTime = allDateSummaryInfoDto.getMinuteOfUsageTime();
        assertEquals(100, minuteOfUsageTime);
    }

}