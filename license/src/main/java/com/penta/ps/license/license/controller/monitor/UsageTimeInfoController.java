package com.penta.ps.license.license.controller.monitor;

import com.penta.ps.license.license.dto.monitor.MonitorUsageTimeDailyInfoDto;
import com.penta.ps.license.license.dto.monitor.MonitorUsageTimeGroupInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoCustNameGroupInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoDto;
import com.penta.ps.license.license.exception.common.InputValueCheckException;
import com.penta.ps.license.license.repository.UsageTimeInfoRepository;
import com.penta.ps.license.license.response.ResponseMonitorUsageTimeDailyInfo;
import com.penta.ps.license.license.response.ResponseMonitorUsageTimeGroupInfo;
import com.penta.ps.license.license.service.monitor.UsageTimeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UsageTimeInfoController {

    private final UsageTimeInfoService usageTimeInfoService;
    private final UsageTimeInfoRepository usageTimeInfoRepository;

    @GetMapping(value = "/usage-time", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getAllUsageTimeInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "10") Integer recordsPerPage,
            @RequestParam(name = "start-date", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(name = "end-date", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
            @RequestParam(name = "serial-no", required = false) String serialNo) {

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        UsageTimeInfoDto.ResponseDto responseDto;

        if (serialNo == null || serialNo.isEmpty())
            responseDto = usageTimeInfoService.findAllPageByDate(currentPageNo, recordsPerPage, startDate, endDate);
        else
            responseDto = usageTimeInfoService.findAllPageByDateAndSerialNo(currentPageNo, recordsPerPage, startDate, endDate, serialNo);

        hashMapResp.put("result", responseDto.getCommonDtoResp().getResult());
        hashMapResp.put("msg", responseDto.getCommonDtoResp().getMsg());
        hashMapResp.put("pageTotalSize", responseDto.getPageDtoResp().getPageTotalSize());
        hashMapResp.put("usageTimeList", responseDto.getUsageTimeInfoDtoResp());

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/specific-usage-time", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getAllSpecificUsageTimeInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "10") Integer recordsPerPage,
            @RequestParam(name = "start-date", required = false, defaultValue = "00000101") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(name = "end-date", required = false, defaultValue = "99991231") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
            @RequestParam(name = "serial-no", required = false) String serialNo,
            @RequestParam(name = "custNm", required = false) String custNm,
            @RequestParam(name = "summary-type", required = false) String summaryType) {

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        UsageTimeInfoDto.ResponseDto responseDto;

        if (summaryType == null || summaryType.isEmpty()) {
            if (serialNo == null || serialNo.isEmpty())
                responseDto = usageTimeInfoService.findAllPageByDate(currentPageNo, recordsPerPage, startDate, endDate);
            else
                responseDto = usageTimeInfoService.findAllPageByDateAndSerialNo(currentPageNo, recordsPerPage, startDate, endDate, serialNo);
        } else if (summaryType.contentEquals("date")) {
            responseDto = usageTimeInfoService.findAllUsageTimeSummaryByDate(startDate, endDate);
        } else if (summaryType.contentEquals("serial-no")) {
            if (serialNo == null || serialNo.isEmpty()) {
                throw new InputValueCheckException();
            }
            responseDto = usageTimeInfoService.findAllUsageTimeSummaryBySerialNo(serialNo, startDate, endDate);
        } else if (summaryType.contentEquals("custNm")) {
            if (custNm == null || custNm.isEmpty()) {
                throw new InputValueCheckException();
            }
            responseDto = usageTimeInfoService.findAllUsageTimeByCustNmAndDateBetweenGroupBySerialNo(custNm, startDate, endDate);
        } else {
            throw new InputValueCheckException();
        }

        hashMapResp.put("result", responseDto.getCommonDtoResp().getResult());
        hashMapResp.put("msg", responseDto.getCommonDtoResp().getMsg());

        if (summaryType == null || summaryType.isEmpty()) {
            hashMapResp.put("pageTotalSize", responseDto.getPageDtoResp().getPageTotalSize());
        }

        hashMapResp.put("usageTimeList", responseDto.getUsageTimeInfoDtoResp());

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/usage-time/monitor/group-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseMonitorUsageTimeGroupInfo> getAllCustNmGroupInfo() {

        return new ResponseEntity<>(ResponseMonitorUsageTimeGroupInfo.builder()
                .usageTimeInfoGroupList(usageTimeInfoService.getAllCustNmGroupInfo())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/usage-time/monitor/daily-all-summary-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getDailyAllSummaryInfo(
            @RequestParam(name = "start-date", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(name = "end-date", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate) {

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();
        HashMap<LocalDate, Object> hashMapDetailResp = new HashMap<LocalDate, Object>();

        UsageTimeInfoDto.ResponseDto responseDto =
                usageTimeInfoService.findAllUsageTimeGroupbyStartDateAndEndDate(startDate, endDate);

        hashMapResp.put("result", responseDto.getCommonDtoResp().getResult());
        hashMapResp.put("msg", responseDto.getCommonDtoResp().getMsg());

        for (UsageTimeInfoDto.UsageTimeInfoDtoResp usageTimeInfoDtoResp : responseDto.getUsageTimeInfoDtoResp()) {
            hashMapDetailResp.put(usageTimeInfoDtoResp.getDate(), usageTimeInfoDtoResp.getMinuteOfUsageTime());
        }

        hashMapResp.put("dailySummaryInfoList", hashMapDetailResp);

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/usage-time/monitor/daily-serialno-summary-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseMonitorUsageTimeDailyInfo> getDailySerialNoSummaryInfo(
            @RequestParam(name = "start-date", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(name = "end-date", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
            @RequestParam(name = "serialno", required = true) String serialNo) {

        UsageTimeInfoDto.ResponseDto responseDto =
                usageTimeInfoService.findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo(startDate, endDate, serialNo);

        List<MonitorUsageTimeDailyInfoDto> dtoList = new ArrayList<>();

        responseDto.getUsageTimeInfoDtoResp().forEach(o -> {
            dtoList.add(MonitorUsageTimeDailyInfoDto.builder()
                    .date(o.getDate().toString())
                    .minuteOfUsageTime(o.getMinuteOfUsageTime())
                    .build());
        });

        return new ResponseEntity<>(ResponseMonitorUsageTimeDailyInfo.builder()
                .monitorUsageTimeDailyInfoDtos(dtoList)
                .build(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/usage-time/monitor/daily-custnm-summary-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseMonitorUsageTimeDailyInfo> getDailyCustNmSummaryInfo(
            @RequestParam(name = "start-date", required = false, defaultValue = "#{T(java.time.LocalDate).now().minusDays(7)}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(name = "end-date", required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
            @RequestParam(name = "custnm", required = true) String custNm) {

        UsageTimeInfoDto.ResponseDto responseDto =
                usageTimeInfoService.findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm(startDate, endDate, custNm);

        List<MonitorUsageTimeDailyInfoDto> dtoList = new ArrayList<>();

        responseDto.getUsageTimeInfoDtoResp().forEach(o -> {
            dtoList.add(MonitorUsageTimeDailyInfoDto.builder()
                    .date(o.getDate().toString())
                    .minuteOfUsageTime(o.getMinuteOfUsageTime())
                    .build());
        });

        return new ResponseEntity<>(ResponseMonitorUsageTimeDailyInfo.builder()
                .monitorUsageTimeDailyInfoDtos(dtoList)
                .build(),
                HttpStatus.OK);
    }

    @PostMapping(value = "/usage-time", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsageTimeInfoDto.CommonDtoResp> saveUsageTimeInfo(@RequestBody UsageTimeInfoDto.SaveUsageTimeDtoReq saveUsageTimeDtoReq) {
        UsageTimeInfoDto.CommonDtoResp commonDtoResp = usageTimeInfoService.saveUsageTimeInfo(saveUsageTimeDtoReq);

        return new ResponseEntity<UsageTimeInfoDto.CommonDtoResp>(commonDtoResp, HttpStatus.OK);
    }

    @PutMapping(value = "/usage-time", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsageTimeInfoDto.CommonDtoResp> updateUsageTimeInfo(@RequestBody UsageTimeInfoDto.UpdateUsageTimeDtoReq updateUsageTimeDtoReq) {

        UsageTimeInfoDto.CommonDtoResp commonDtoResp = usageTimeInfoService.updateUsageTimeInfo(updateUsageTimeDtoReq);

        return new ResponseEntity<UsageTimeInfoDto.CommonDtoResp>(commonDtoResp, HttpStatus.OK);
    }

}
