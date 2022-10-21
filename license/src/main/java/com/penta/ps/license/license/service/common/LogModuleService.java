package com.penta.ps.license.license.service.common;

import com.penta.ps.license.license.dto.log.EventLogDto;
import com.penta.ps.license.license.dto.log.PolicyLogDto;
import com.penta.ps.license.license.dto.log.SystemLogDto;
import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.service.log.LogService;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class LogModuleService {
    private final ResponseService responseService;
    private final LogService logService;

    public CommonResult SaveEventLog(LogLevelType logLevelType, String msg) {
        logService.saveEventLog(
                EventLogDto
                        .builder()
                        .facility(ManagerType.LICENSE)
                        .logLevel(logLevelType)
                        .msg(msg)
                        .build());

        return responseService.getSuccessResult();
    }

    public CommonResult SaveSystemLog(LogLevelType logLevelType, String msg) {
        logService.saveSystemLog(
                SystemLogDto
                        .builder()
                        .facility(ManagerType.LICENSE)
                        .logLevel(logLevelType)
                        .msg(msg)
                        .build());

        return responseService.getSuccessResult();
    }

    public CommonResult SavePolicyLog(LogLevelType logLevelType, String msg) {
        logService.savePolicyLog(
                PolicyLogDto
                        .builder()
                        .facility(ManagerType.LICENSE)
                        .logLevel(logLevelType)
                        .msg(msg)
                        .build());

        return responseService.getSuccessResult();
    }
}
