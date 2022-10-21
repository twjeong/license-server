package com.penta.ps.license.license.controller.license;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.penta.ps.license.license.dto.contract.ContractExtendedInfoDto;
import com.penta.ps.license.license.dto.license.ContactFindAllInfoDto;
import com.penta.ps.license.license.dto.license.LicenseCertificateDto;
import com.penta.ps.license.license.dto.license.LicenseInfoDto;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.exception.license.SearchSerialNoException;
import com.penta.ps.license.license.mapper.LicenseMapper;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.service.license.LicenseService;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ContractController {

    private final ResponseService responseService;
    private final LicenseService licenseService;
    private final LicenseInfoJpaRepo licenseInfoJpaRepo;

    @GetMapping(value = "/license-ext-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult getLicenseExtendInfo(@RequestParam(value = "serial-no", required = true) String serialNo) throws JsonProcessingException {

        ContractExtendedInfoDto.ContractExtendedInfo contractExtendedInfo = licenseService.getContractExtendedInfo(serialNo);

        return responseService.getSingleResultWithContractExtendedField(
                contractExtendedInfo.getStartDate(),
                contractExtendedInfo.getEndDate(),
                contractExtendedInfo.getContractExtendedInfoDto());
    }

    @GetMapping(value = "/common-agent/license-certificate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult getLicenseCertificate(@RequestParam(value = "license-id", required = true) String serialNo)
            throws IOException, ParseException, GeneralSecurityException, OperatorCreationException {

        Optional<LicenseInfo> tempLicenseInfo = licenseInfoJpaRepo.findBySerialNo(serialNo);

        if (!tempLicenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        LicenseInfoDto licenseInfoDto = LicenseMapper.INSTANCE.entityToLicenseInfoDto(tempLicenseInfo.get());

        LicenseCertificateDto.LicenseCertificate licenseCertificate = new LicenseCertificateDto.LicenseCertificate();
        LicenseCertificateDto.Optional optional = new LicenseCertificateDto.Optional();

        licenseCertificate.setLicenseId(licenseInfoDto.getSerialNo());
        licenseCertificate.setNodeId(licenseInfoDto.getNodeId());
        licenseCertificate.setContractNumber(licenseInfoDto.getOrderNum());
        licenseCertificate.setCpuCount(licenseInfoDto.getCore());
        licenseCertificate.setLicenseType(licenseInfoDto.getLicenseType().getName());
        licenseCertificate.setProductId(licenseInfoDto.getInstanceNm());    // hwsn을 intancenm으로 대체함
        licenseCertificate.setProductName(licenseInfoDto.getComponentName());
        licenseCertificate.setProductVersion(licenseInfoDto.getComponentVersion());

        optional.setAC(licenseInfoDto.getLisExpAct());
        optional.setSV(licenseInfoDto.getWebCnt());
        licenseCertificate.setOptional(optional);

        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date now = sdf.parse(sdf.format(new Date()));

        //        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //초 차이
        //        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //분 차이
        //        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //시 차이
        //        long diffDays = diffSec / (24*60*60); //일자수 차이
        if (licenseInfoDto.getComponentName().contentEquals(TypeDefine.ProductName.WAPPLES.getOrgName())) {
            if (((licenseInfoDto.getLicenseEndDate().getTime() - now.getTime()) / (1000 * 24 * 60 * 60)) <= 30) {
                cal.setTime(licenseInfoDto.getLicenseEndDate());
            } else {
                cal.setTime(now);
                cal.add(Calendar.DATE, 30);
                cal.add(Calendar.HOUR, 23);
                cal.add(Calendar.MINUTE, 59);
                cal.add(Calendar.SECOND, 59);
            }
        } else {
            cal.setTime(licenseInfoDto.getLicenseEndDate());
        }

        licenseInfoDto.setLicenseIssueDate(now);
        licenseInfoDto.setLicenseExpiredDate(cal.getTime());
        licenseInfoDto.setLastUsageTime(new Date());
        licenseInfoJpaRepo.save(LicenseMapper.INSTANCE.licenseInfoDtoToEntity(licenseInfoDto));

        return responseService.getSingleResultWithCertificate(
                licenseInfoDto.getLicenseIssueDate(),
                licenseInfoDto.getLicenseExpiredDate(),
                licenseCertificate);
    }
}
