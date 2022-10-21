package com.penta.ps.license.license.controller.license;

import com.penta.ps.license.license.dto.license.SaveUsageTimeDtoReq;
import com.penta.ps.license.license.dto.license.UpdateUsageTimeDtoReq;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoDto;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.exception.license.LicenseStatusIsNotUsedException;
import com.penta.ps.license.license.exception.license.LicenseTypeIsNotCloudMeteringException;
import com.penta.ps.license.license.exception.license.SearchSerialNoException;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.service.monitor.UsageTimeInfoService;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MonitorController {
    private final LicenseInfoJpaRepo licenseInfoJpaRepo;
    private final UsageTimeInfoService usageTimeInfoService;

    @Transactional()
    @PostMapping("/licenses/monitor/usage-time")
    public UsageTimeInfoDto.CommonDtoResp insertUsageTimeForMonitor(@RequestBody SaveUsageTimeDtoReq saveUsageTimeDtoReq) {
        Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(saveUsageTimeDtoReq.getSerialNo());

        // license_info 테아블에 해당 정보가 존재해야함.
        if (!licenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        saveUsageTimeDtoReq.setCustNm(licenseInfo.get().getCustNm());

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = usageTimeInfoService.saveUsageTimeInfo(
                UsageTimeInfoDto.SaveUsageTimeDtoReq
                        .builder()
                        .serialNo(saveUsageTimeDtoReq.getSerialNo())
                        .custNm(saveUsageTimeDtoReq.getCustNm())
                        .date(saveUsageTimeDtoReq.getDate())
                        .minuteOfUsageTime(saveUsageTimeDtoReq.getMinuteOfUsageTime())
                        .createTime(saveUsageTimeDtoReq.getCreateTime())
                        .modifyTime(saveUsageTimeDtoReq.getModifyTime())
                        .build());

        return commonDtoResp;
    }

    @Transactional()
    @PutMapping("/licenses/monitor/usage-time")
    public UsageTimeInfoDto.CommonDtoResp updateUsageTimeForMonitor(@RequestBody UpdateUsageTimeDtoReq updateUsageTimeDtoReq) throws SSLException {
        Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(updateUsageTimeDtoReq.getSerialNo());

        if (!licenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        if (!licenseInfo.get().getStatus().equals(TypeDefine.Status.LICENSE_IN_USE)) {
            throw new LicenseStatusIsNotUsedException();
        }

        if (!licenseInfo.get().getLicenseType().equals(TypeDefine.LicenseType.CLOUD_METERING)) {
            throw new LicenseTypeIsNotCloudMeteringException();
        }

        updateUsageTimeDtoReq.setCustNm(licenseInfo.get().getCustNm());

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = usageTimeInfoService.updateUsageTimeInfo(
                UsageTimeInfoDto.UpdateUsageTimeDtoReq
                        .builder()
                        .serialNo(updateUsageTimeDtoReq.getSerialNo())
                        .custNm(updateUsageTimeDtoReq.getCustNm())
                        .date(updateUsageTimeDtoReq.getDate())
                        .minuteOfUsageTime(updateUsageTimeDtoReq.getMinuteOfUsageTime())
                        .createTime(updateUsageTimeDtoReq.getCreateTime())
                        .modifyTime(updateUsageTimeDtoReq.getModifyTime())
                        .build());

        return commonDtoResp;
    }
}
