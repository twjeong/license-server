package com.penta.ps.license.license.domain;

import com.penta.ps.license.license.dto.license.MonitorUsageTimeInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoDto;
import com.penta.ps.license.license.entity.BillingInfoEntity;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.exception.common.NotFoundDataException;
import com.penta.ps.license.license.repository.BillingInfoRepository;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.monitor.UsageTimeInfoService;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class Billing {
    private String serialNo;
    private String custNm;
    private Integer usageHour;
    private String componentName;
    private String category;
    private Integer core;
    private Integer providedValue;
    private Integer tax;
    private Integer amount;
    private Integer lastAmount;
    private Integer variationAmount;

    //constructor injection
    private LogModuleService logModuleService;
    private LicenseInfoJpaRepo licenseInfoJpaRepo;
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private UsageTimeInfoService usageTimeInfoService;

    @Autowired
    public Billing(LogModuleService logModuleService,
                   LicenseInfoJpaRepo licenseInfoJpaRepo,
                   BillingInfoRepository billingInfoRepository) {

        this.logModuleService = logModuleService;
        this.licenseInfoJpaRepo = licenseInfoJpaRepo;
        this.billingInfoRepository = billingInfoRepository;
    }

    @Builder
    public Billing(
            String serialNo,
            String custNm,
            Integer usageHour,
            String componentName,
            String category,
            Integer core,
            Integer providedValue,
            Integer tax,
            Integer amount,
            Integer lastAmount,
            Integer variationAmount
    ) {
        this.serialNo = serialNo;
        this.custNm = custNm;
        this.usageHour = usageHour;
        this.componentName = componentName;
        this.category = category;
        this.core = core;
        this.providedValue = providedValue;
        this.tax = tax;
        this.amount = amount;
        this.lastAmount = lastAmount;
        this.variationAmount = variationAmount;
    }

    private List<MonitorUsageTimeInfoDto> getUsageTimeList(Integer year, Integer month, String custNm) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        UsageTimeInfoDto.ResponseDto responseDto
                = usageTimeInfoService.findAllUsageTimeByCustNmAndDateBetweenGroupBySerialNo(custNm, startDate, endDate);
/*
        if (responseDto.getCommonDtoResp().getResult() != 0
                || responseDto.getUsageTimeInfoDtoResp() == null) {
            logModuleService.SaveEventLog(LogLevelType.ERROR,
                    "Failed to get specific-usage-time info");
            throw new GetToManagerException();
        }
*/
        List<MonitorUsageTimeInfoDto> monitorUsageTimeInfoDtos = new ArrayList<>();
        for (UsageTimeInfoDto.UsageTimeInfoDtoResp usageTimeInfoDtoResp : responseDto.getUsageTimeInfoDtoResp()) {
            monitorUsageTimeInfoDtos
                    .add(
                            MonitorUsageTimeInfoDto
                                    .builder()
//                                    .createTime(usageTimeInfoDtoResp.getCreateTime())
                                    .custNm(custNm)
//                                    .date(sdf.format(usageTimeInfoDtoResp.getDate()))
                                    .minuteOfUsageTime(usageTimeInfoDtoResp.getMinuteOfUsageTime())
//                                    .modifyTime(usageTimeInfoDtoResp.getModifyTime())
                                    .serialNo(usageTimeInfoDtoResp.getSerialNo())
                                    .build());
        }

        return monitorUsageTimeInfoDtos;
    }

    public List<Billing> generateBillingList(Integer year, Integer month, String custNm) {
        final YearMonth lastMonth = YearMonth.of(year, month).minusMonths(1);

        //get usageTime & serialNo list from monitor module
        List<MonitorUsageTimeInfoDto> monitorUsageTimeInfoDtoList = getUsageTimeList(year, month, custNm);

        //get last month billing info
        List<MonitorUsageTimeInfoDto> lastMonitorUsageTimeInfoDtoList = getUsageTimeList(lastMonth.getYear(), lastMonth.getMonthValue(), custNm);

        //get license info from license entity
        List<String> serialNoList = new ArrayList<>();
        monitorUsageTimeInfoDtoList.forEach(o -> serialNoList.add(o.getSerialNo()));
        List<LicenseInfo> licenseInfos = licenseInfoJpaRepo.findBySerialNoIn(serialNoList);

        //generate common data
        List<Billing> billingList = new ArrayList<>();

        for (MonitorUsageTimeInfoDto monitorUsageTimeInfoDto : monitorUsageTimeInfoDtoList) {

            LicenseInfo licenseInfo = licenseInfos.stream()
                    .filter(o -> monitorUsageTimeInfoDto.getSerialNo().equals(o.getSerialNo()))
                    .findAny()
                    .orElse(null);

            MonitorUsageTimeInfoDto lastMonitorUsageTimeInfoDto = lastMonitorUsageTimeInfoDtoList.stream()
                    .filter(o -> monitorUsageTimeInfoDto.getSerialNo().equals(o.getSerialNo()))
                    .findAny()
                    .orElse(null);

            final String componentName = licenseInfo.getComponentName();
            final String category = componentName.substring(0, 2);
            final int core = licenseInfo.getCore();

            final int usageTime = monitorUsageTimeInfoDto.getMinuteOfUsageTime();
            final int providedValue = calculateAmount(componentName, core, usageTime / 60);

            final int lastProvidedValue = (lastMonitorUsageTimeInfoDto == null) ? 0 :
                    calculateAmount(componentName, core, lastMonitorUsageTimeInfoDto.getMinuteOfUsageTime() / 60);

            billingList.add(
                    Billing.builder()
                            .serialNo(monitorUsageTimeInfoDto.getSerialNo())
                            .componentName(componentName)
                            .category(category)
                            .core(core)
                            .usageHour(usageTime / 60)
                            .providedValue(providedValue)
                            .tax(providedValue / 10)
                            .amount(providedValue + (providedValue / 10))
                            .lastAmount(lastProvidedValue + (lastProvidedValue / 10))
                            .variationAmount(providedValue - lastProvidedValue
                                    + ((providedValue - lastProvidedValue) / 10))
                            .build());
        }

        return billingList;
    }

    public void generateBillingInfo() {

        if (billingInfoRepository.findAll().isEmpty()) {
            billingInfoRepository.saveAll(Arrays.asList(
                new BillingInfoEntity("BA-SCP", TypeDefine.BillingType.INSTANCE, 0, 700, 0),
                new BillingInfoEntity("KE-WIN", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("DE-MYQ", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("DP-MSQ", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("DP-ORA", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("BA-UND", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("BA-P11", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("BA-POS", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("KE-LNX", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("DA", TypeDefine.BillingType.BASE, 2, 100, 500),
                new BillingInfoEntity("SG-KMS_SA", TypeDefine.BillingType.FIXED, 4, 500, 0),
                new BillingInfoEntity("SG-KMS_SA", TypeDefine.BillingType.FIXED, 8, 1000, 0),
                new BillingInfoEntity("SG-KMS_SA", TypeDefine.BillingType.FIXED, 16, 1500, 0)
            ));
        }
    }

    private Integer calculateAmount(String componentName, Integer core, Integer usageHour) {

        BillingInfoEntity billingInfo;

        Integer price = 0;

        List<BillingInfoEntity> billingInfoEntities = billingInfoRepository.findByComponentName(componentName);
        if (billingInfoEntities.isEmpty()) {
            throw new NotFoundDataException();
        }

        if (billingInfoEntities.size() == 1) {
            billingInfo = billingInfoEntities.get(0);
        } else {
            billingInfo = billingInfoRepository.findByComponentNameAndCore(componentName, core)
                    .orElseThrow(NotFoundDataException::new);
        }

        TypeDefine.BillingType billingType = billingInfo.getBillingType();

        if (billingType == TypeDefine.BillingType.INSTANCE ||
            billingType == TypeDefine.BillingType.FIXED) {

            price = billingInfo.getHourlyPrice();

        } else {
            price = billingInfo.getBasePrice();

            if (core > billingInfo.getCore()) {
                price = price + ((core - billingInfo.getCore()) * billingInfo.getHourlyPrice());
            }
        }

        return (usageHour != 0) ? usageHour * price : price;
    }
}
