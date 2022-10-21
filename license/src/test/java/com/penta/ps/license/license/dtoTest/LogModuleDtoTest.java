package com.penta.ps.license.license.dtoTest;

import com.penta.ps.license.license.dto.common.LogModuleDto;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogModuleDtoTest {

    @Test
    void getEventLogReqDto() {
        final LogModuleDto.EventLogReqDto eventLogReqDto
                = LogModuleDto.EventLogReqDto.builder().facility(ManagerType.MONITOR).logLevel(LogLevelType.INFO).msg("test msg1").build();

        assertEquals(ManagerType.MONITOR, eventLogReqDto.getFacility());
        assertEquals(LogLevelType.INFO, eventLogReqDto.getLogLevel());
        assertEquals("test msg1", eventLogReqDto.getMsg());
    }

}