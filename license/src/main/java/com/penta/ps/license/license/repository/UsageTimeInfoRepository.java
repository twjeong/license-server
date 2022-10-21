package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto;
import com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoCustNameGroupInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeSerialNoGroupDto;
import com.penta.ps.license.license.entity.UsageTimeInfoEntity;
import com.penta.ps.license.license.entity.UsageTimeInfoEntityPrimaryKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface UsageTimeInfoRepository extends JpaRepository<UsageTimeInfoEntity, UsageTimeInfoEntityPrimaryKey> {

    // (전체, 결과 다건) 날짜 조건 검색
    public Page<UsageTimeInfoEntity> findPageByDateBetween(Pageable pageable, LocalDate startDate, LocalDate endDate);

    // (전체, 결과 다건) 날짜, 시리얼번호 검색
    public Page<UsageTimeInfoEntity> findPageByDateBetweenAndSerialNo(Pageable pageable, LocalDate startDate, LocalDate endDate, String SerialNo);

    // (개별, 결과 단건 null가능) 날짜, 시리얼번호 검색
    @Nullable
    public UsageTimeInfoEntity findByDateAndSerialNo(LocalDate date, String SerialNo);

    // 모니터링 내역 저장
    public UsageTimeInfoEntity save(UsageTimeInfoEntity usageTimeInfoEntity);


    @Query("SELECT new com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto(SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.date BETWEEN :startDate AND :endDate ")
    public List<AllDateSummaryInfoDto> findAllUsageTimeSummaryByDate(@Param("startDate") LocalDate startDate,
                                                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto(SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.serialNo = :serialNo " +
            "    and s.date between :startDate and :endDate ")
    public List<AllDateSummaryInfoDto> findAllUsageTimeSummaryBySerialNo(@Param("serialNo") String serialNo,
                                                                         @Param("startDate") LocalDate startDate,
                                                                         @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto(SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.custNm = :custNm " +
            "    and s.date between :startDate and :endDate ")
    public List<AllDateSummaryInfoDto> findAllUsageTimeSummaryByCustNm(@Param("custNm") String custNm,
                                                                       @Param("startDate") LocalDate startDate,
                                                                       @Param("endDate") LocalDate endDate);


    @Query("SELECT new com.penta.ps.license.license.dto.monitor.UsageTimeInfoCustNameGroupInfoDto(s.custNm, s.serialNo) " +
            "FROM UsageTimeInfoEntity s " +
            "GROUP BY s.custNm, s.serialNo")
    public List<UsageTimeInfoCustNameGroupInfoDto> findGroupByCustNmAndSerialNo();

    @Query(value = "SELECT new com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto(s.date, SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.date between :startDate and :endDate " +
            "GROUP BY s.date ")
    public List<DailySummaryInfoDto> findAllUsageTimeGroupbyStartDateAndEndDate(@Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT new com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto(s.date, SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.date between :startDate and :endDate " +
            "AND s.serialNo = :serialNo " +
            "GROUP BY s.date ")
    public List<DailySummaryInfoDto> findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo(@Param("startDate") LocalDate startDate,
                                                                                           @Param("endDate") LocalDate endDate,
                                                                                           @Param("serialNo") String serialNo);

    @Query(value = "SELECT new com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto(s.date, SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.date between :startDate and :endDate " +
            "AND s.custNm = :custNm " +
            "GROUP BY s.date ")
    public List<DailySummaryInfoDto> findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm(@Param("startDate") LocalDate startDate,
                                                                                         @Param("endDate") LocalDate endDate,
                                                                                         @Param("custNm") String custNm);

    @Query(value = "SELECT new com.penta.ps.license.license.dto.monitor.UsageTimeSerialNoGroupDto(s.serialNo, SUM(s.minuteOfUsageTime)) " +
            "FROM UsageTimeInfoEntity s " +
            "WHERE s.date between :startDate and :endDate " +
            "AND s.custNm = :custNm " +
            "GROUP BY s.serialNo ")
    public List<UsageTimeSerialNoGroupDto> findAllUsageTimeByCustNmAndDateBetweenGroupBySerialNo(@Param("custNm") String custNm,
                                                                                                 @Param("startDate") LocalDate startDate,
                                                                                                 @Param("endDate") LocalDate endDate);
}