package com.penta.ps.license.license.service.monitor;

import com.penta.ps.license.license.dto.monitor.MonitorUsageTimeGroupInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoCustNameGroupInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeSerialNoGroupDto;
import com.penta.ps.license.license.entity.UsageTimeInfoEntity;
import com.penta.ps.license.license.exception.common.PrimarykeyDuplicationException;
import com.penta.ps.license.license.mapper.UsageTimeInfoMapper;
import com.penta.ps.license.license.repository.UsageTimeInfoRepository;
import com.penta.ps.license.license.type.ResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@RequiredArgsConstructor
public class UsageTimeInfoService {

    private final UsageTimeInfoRepository usageTimeInfoRepository;

    public UsageTimeInfoDto.ResponseDto findAllPage(UsageTimeInfoDto.UsageTimeDtoReq usageTimeDtoReq) {

        Page<UsageTimeInfoEntity> usageTimeInfoEntityPage =
                usageTimeInfoRepository.findAll(PageRequest.of(usageTimeDtoReq.getCurrentPageNo() - 1, usageTimeDtoReq.getRecordsPerPage()));

        List<UsageTimeInfoEntity> usageTimeInfoEntities = usageTimeInfoEntityPage.getContent();

        UsageTimeInfoDto.PageDtoResp pageDtoResp = UsageTimeInfoDto.PageDtoResp.builder()
                .pageTotalSize((long) usageTimeInfoEntityPage.getTotalPages())
                .build();

        List<UsageTimeInfoDto.UsageTimeInfoDtoResp> usageTimeInfoDtoResp = new ArrayList<>();

        for (UsageTimeInfoEntity usageTimeInfoEntity : usageTimeInfoEntities) {
            usageTimeInfoDtoResp.add(UsageTimeInfoDto.UsageTimeInfoDtoResp.builder()
                    .serialNo(usageTimeInfoEntity.getSerialNo())
                    .date(usageTimeInfoEntity.getDate())
                    .custNm(usageTimeInfoEntity.getCustNm())
                    .minuteOfUsageTime(usageTimeInfoEntity.getMinuteOfUsageTime())
                    .createTime(usageTimeInfoEntity.getCreateTime())
                    .modifyTime(usageTimeInfoEntity.getModifyTime())
                    .build());
        }

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = UsageTimeInfoDto.CommonDtoResp.builder()
                .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                .build();

        return new UsageTimeInfoDto.ResponseDto(commonDtoResp, pageDtoResp, usageTimeInfoDtoResp);
    }


    public UsageTimeInfoDto.ResponseDto findAllPageByDate(Integer currentPageNo, Integer recordsPerPage,
                                                          LocalDate startDate, LocalDate endDate) {

        Page<UsageTimeInfoEntity> usageTimeInfoEntityPage =
                usageTimeInfoRepository.findPageByDateBetween(PageRequest.of(currentPageNo - 1, recordsPerPage), startDate, endDate);

        List<UsageTimeInfoEntity> usageTimeInfoEntities = usageTimeInfoEntityPage.getContent();

        UsageTimeInfoDto.PageDtoResp pageDtoResp = UsageTimeInfoDto.PageDtoResp.builder()
                .pageTotalSize(usageTimeInfoRepository.count())
                .build();

        List<UsageTimeInfoDto.UsageTimeInfoDtoResp> usageTimeInfoDtoResp = new ArrayList<>();

        for (UsageTimeInfoEntity usageTimeInfoEntity : usageTimeInfoEntities) {
            usageTimeInfoDtoResp.add(UsageTimeInfoDto.UsageTimeInfoDtoResp.builder()
                    .serialNo(usageTimeInfoEntity.getSerialNo())
                    .date(usageTimeInfoEntity.getDate())
                    .custNm(usageTimeInfoEntity.getCustNm())
                    .minuteOfUsageTime(usageTimeInfoEntity.getMinuteOfUsageTime())
                    .createTime(usageTimeInfoEntity.getCreateTime())
                    .modifyTime(usageTimeInfoEntity.getModifyTime())
                    .build());
        }

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = UsageTimeInfoDto.CommonDtoResp
                .builder()
                .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                .build();

        return new UsageTimeInfoDto.ResponseDto(commonDtoResp, pageDtoResp, usageTimeInfoDtoResp);
    }

    public UsageTimeInfoDto.ResponseDto findAllPageByDateAndSerialNo(Integer currentPageNo, Integer recordsPerPage,
                                                                     LocalDate startDate, LocalDate endDate, String serialNo) {

        Page<UsageTimeInfoEntity> usageTimeInfoEntityPage =
                usageTimeInfoRepository.findPageByDateBetweenAndSerialNo(PageRequest.of(currentPageNo - 1, recordsPerPage), startDate, endDate, serialNo);

        List<UsageTimeInfoEntity> usageTimeInfoEntities = usageTimeInfoEntityPage.getContent();

        UsageTimeInfoDto.PageDtoResp pageDtoResp = UsageTimeInfoDto.PageDtoResp.builder()
                .pageTotalSize(usageTimeInfoRepository.count())
                .build();

        List<UsageTimeInfoDto.UsageTimeInfoDtoResp> usageTimeInfoDtoResp = new ArrayList<>();

        for (UsageTimeInfoEntity usageTimeInfoEntity : usageTimeInfoEntities) {
            usageTimeInfoDtoResp.add(UsageTimeInfoDto.UsageTimeInfoDtoResp.builder()
                    .serialNo(usageTimeInfoEntity.getSerialNo())
                    .date(usageTimeInfoEntity.getDate())
                    .custNm(usageTimeInfoEntity.getCustNm())
                    .minuteOfUsageTime(usageTimeInfoEntity.getMinuteOfUsageTime())
                    .createTime(usageTimeInfoEntity.getCreateTime())
                    .modifyTime(usageTimeInfoEntity.getModifyTime())
                    .build());
        }

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = UsageTimeInfoDto.CommonDtoResp
                .builder()
                .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                .build();

        return new UsageTimeInfoDto.ResponseDto(commonDtoResp, pageDtoResp, usageTimeInfoDtoResp);
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeSummaryByDate(LocalDate startDate, LocalDate endDate) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp
                        .builder()
                        .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                        .build(),
                UsageTimeInfoMapper.INSTANCE.allDateSumarryDtosToTimeInfoDtos(usageTimeInfoRepository.findAllUsageTimeSummaryByDate(startDate, endDate)));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeSummaryBySerialNo(String serialNo, LocalDate startDate, LocalDate endDate) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.allDateSumarryDtosToTimeInfoDtos(
                        usageTimeInfoRepository.findAllUsageTimeSummaryBySerialNo(serialNo, startDate, endDate)));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeSummaryByCustNm(String custNm, LocalDate startDate, LocalDate endDate) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.allDateSumarryDtosToTimeInfoDtos(
                        usageTimeInfoRepository.findAllUsageTimeSummaryByCustNm(custNm, startDate, endDate)));
    }

    public UsageTimeInfoDto.ResponseDto findAllGroupByCustNmAndSerialNo() {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.dtosToDtos(usageTimeInfoRepository.findGroupByCustNmAndSerialNo()));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeGroupbyStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.dailySumarryDtosToTimeInfoDtos(
                        usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDate(startDate, endDate)));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo(LocalDate startDate,
                                                                                              LocalDate endDate,
                                                                                              String serialNo) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.dailySumarryDtosToTimeInfoDtos(
                        usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo(startDate, endDate, serialNo)));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm(LocalDate startDate,
                                                                                            LocalDate endDate,
                                                                                            String custNm) {
        return new UsageTimeInfoDto.ResponseDto(
                UsageTimeInfoDto.CommonDtoResp.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                UsageTimeInfoMapper.INSTANCE.dailySumarryDtosToTimeInfoDtos(
                        usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm(startDate, endDate, custNm)));
    }

    public UsageTimeInfoDto.ResponseDto findAllUsageTimeByCustNmAndDateBetweenGroupBySerialNo(String custNm, LocalDate startDate, LocalDate endDate) {
        List<UsageTimeSerialNoGroupDto> usageTimeSerialNoGroupDtos
                = usageTimeInfoRepository.findAllUsageTimeByCustNmAndDateBetweenGroupBySerialNo(custNm, startDate, endDate);

        UsageTimeInfoDto.CommonDtoResp commonDtoResp = new UsageTimeInfoDto.CommonDtoResp();
        List<UsageTimeInfoDto.UsageTimeInfoDtoResp> usageTimeInfoDtoResps = new ArrayList<>();

        if (usageTimeSerialNoGroupDtos.size() == 0) {
            commonDtoResp = UsageTimeInfoDto.CommonDtoResp.builder()
                    .result(ResultType.FAIL.ordinal())
                    .msg(ResultType.FAIL.name())
                    .build();
        } else {
            commonDtoResp = UsageTimeInfoDto.CommonDtoResp.builder()
                    .result(ResultType.SUCCESS.ordinal())
                    .msg(ResultType.SUCCESS.name())
                    .build();
            usageTimeInfoDtoResps = UsageTimeInfoMapper.INSTANCE.usageTimeSerialNoGroupDtosToTimeInfoDtos(usageTimeSerialNoGroupDtos);
        }

        return UsageTimeInfoDto.ResponseDto.builder()
                .commonDtoResp(commonDtoResp)
                .usageTimeInfoDtoResp(usageTimeInfoDtoResps)
                .build();
    }

    public UsageTimeInfoDto.CommonDtoResp saveUsageTimeInfo(UsageTimeInfoDto.SaveUsageTimeDtoReq saveUsageTimeDtoReq) {

        if (saveUsageTimeDtoReq.getCreateTime() == null) {
            saveUsageTimeDtoReq.setCreateTime(LocalDateTime.now());
        }

        if (saveUsageTimeDtoReq.getModifyTime() == null) {
            saveUsageTimeDtoReq.setModifyTime(LocalDateTime.now());
        }

        UsageTimeInfoEntity selUsageTimeInfoEntity
                = usageTimeInfoRepository.findByDateAndSerialNo(saveUsageTimeDtoReq.getDate(),
                saveUsageTimeDtoReq.getSerialNo());

        if (selUsageTimeInfoEntity != null) {
            if (saveUsageTimeDtoReq.getSerialNo().contentEquals(selUsageTimeInfoEntity.getSerialNo()) ||
                    saveUsageTimeDtoReq.getDate().isEqual(selUsageTimeInfoEntity.getDate())) {
                throw new PrimarykeyDuplicationException("Already Insert Data");
            }
        }

        UsageTimeInfoEntity insUsageTimeInfoEntity
                = usageTimeInfoRepository.save(UsageTimeInfoMapper.INSTANCE.toEntity(saveUsageTimeDtoReq));

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = UsageTimeInfoDto.CommonDtoResp
                .builder()
                .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                .build();

        return commonDtoResp;
    }

    public UsageTimeInfoDto.CommonDtoResp updateUsageTimeInfo(UsageTimeInfoDto.UpdateUsageTimeDtoReq updateUsageTimeDtoReq) {

        if (updateUsageTimeDtoReq.getCreateTime() == null) {
            updateUsageTimeDtoReq.setCreateTime(LocalDateTime.now());
        }

        if (updateUsageTimeDtoReq.getModifyTime() == null) {
            updateUsageTimeDtoReq.setModifyTime(LocalDateTime.now());
        }

        UsageTimeInfoEntity selUsageTimeInfoEntity
                = usageTimeInfoRepository.findByDateAndSerialNo(updateUsageTimeDtoReq.getDate(),
                updateUsageTimeDtoReq.getSerialNo());

         /* 기존데이터 있는 경우 사용시간 누적
+        * 기존데이터 없는 경우에는 입력값 그대로 insert */
        if (selUsageTimeInfoEntity != null) {
            updateUsageTimeDtoReq.setMinuteOfUsageTime(updateUsageTimeDtoReq.getMinuteOfUsageTime() +
                    selUsageTimeInfoEntity.getMinuteOfUsageTime());
        }

        UsageTimeInfoEntity uptUsageTimeInfoEntity
                = usageTimeInfoRepository.save(UsageTimeInfoMapper.INSTANCE.toEntity(updateUsageTimeDtoReq));

        UsageTimeInfoDto.CommonDtoResp commonDtoResp
                = UsageTimeInfoDto.CommonDtoResp
                .builder()
                .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                .build();

        return commonDtoResp;
    }

    public List<MonitorUsageTimeGroupInfoDto> getAllCustNmGroupInfo() {

        Map<String, List<UsageTimeInfoCustNameGroupInfoDto>> group = usageTimeInfoRepository.findGroupByCustNmAndSerialNo().stream()
                .collect(groupingBy(UsageTimeInfoCustNameGroupInfoDto::getCustNm, toList()));

        List<MonitorUsageTimeGroupInfoDto> dtoList = new ArrayList<>();

        group.forEach((c, l) -> {

            MonitorUsageTimeGroupInfoDto dto = MonitorUsageTimeGroupInfoDto.builder()
                    .custName(c)
                    .serialNo(new ArrayList<>())
                    .build();

            l.forEach(o -> {
                dto.getSerialNo().add(o.getSerialNo());
            });

            dtoList.add(dto);
        });

        return dtoList;
    }
}
