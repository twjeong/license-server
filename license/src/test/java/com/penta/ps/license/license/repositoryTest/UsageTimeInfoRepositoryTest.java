package com.penta.ps.license.license.repositoryTest;

import com.penta.ps.license.license.dto.monitor.AllDateSummaryInfoDto;
import com.penta.ps.license.license.dto.monitor.DailySummaryInfoDto;
import com.penta.ps.license.license.dto.monitor.UsageTimeInfoCustNameGroupInfoDto;
import com.penta.ps.license.license.entity.UsageTimeInfoEntity;
import com.penta.ps.license.license.repository.UsageTimeInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

//@DataJpaTest
@SpringBootTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
@DisplayName("UsageTimeInfo Repository Test")
class UsageTimeInfoRepositoryTest {

    @Autowired
    UsageTimeInfoRepository usageTimeInfoRepository;

    @AfterEach
    public void cleanup() {
        usageTimeInfoRepository.deleteAll();
    }

    @Test
    @DisplayName("save()")
    void save() {
        for (int i = 0; i < 10; i++) {
            UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                    .serialNo("serialNo_" + i)
                    .date(LocalDate.now())
                    .custNm("custNm_" + i)
                    .minuteOfUsageTime(i)
                    .createTime(LocalDateTime.now().withNano(0))
                    .modifyTime(LocalDateTime.now().withNano(0))
                    .build();

            UsageTimeInfoEntity usageTimeInfoEntity1 = usageTimeInfoRepository.save(usageTimeInfoEntity);

            assertThat(usageTimeInfoEntity1.getSerialNo(), is(equalTo(usageTimeInfoEntity.getSerialNo())));
            assertThat(usageTimeInfoEntity1.getDate(), is(equalTo(usageTimeInfoEntity.getDate())));
            assertThat(usageTimeInfoEntity1.getCustNm(), is(equalTo(usageTimeInfoEntity.getCustNm())));
            assertThat(usageTimeInfoEntity1.getMinuteOfUsageTime(), is(equalTo(usageTimeInfoEntity.getMinuteOfUsageTime())));
            assertThat(usageTimeInfoEntity1.getCreateTime(), is(equalTo(usageTimeInfoEntity.getCreateTime())));
            assertThat(usageTimeInfoEntity1.getModifyTime(), is(equalTo(usageTimeInfoEntity.getModifyTime())));
        }
    }

    @Test
    @DisplayName("findPageByDateBetween()")
    void findPageByDateBetween() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(100)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        Page<UsageTimeInfoEntity> usageTimeInfoEntityList
                = usageTimeInfoRepository.findPageByDateBetween(PageRequest.of(0, 100), LocalDate.now(), LocalDate.now());

        List<UsageTimeInfoEntity> usageTimeInfoEntities = usageTimeInfoEntityList.getContent();

        assertThat(usageTimeInfoEntities.get(0).getSerialNo(), is(usageTimeInfoEntity.getSerialNo()));
        assertThat(usageTimeInfoEntities.get(0).getDate(), is(usageTimeInfoEntity.getDate()));
        assertThat(usageTimeInfoEntities.get(0).getCustNm(), is(usageTimeInfoEntity.getCustNm()));
        assertThat(usageTimeInfoEntities.get(0).getMinuteOfUsageTime(), is(usageTimeInfoEntity.getMinuteOfUsageTime()));
        assertThat(usageTimeInfoEntities.get(0).getCreateTime(), is(usageTimeInfoEntity.getCreateTime()));
        assertThat(usageTimeInfoEntities.get(0).getModifyTime(), is(usageTimeInfoEntity.getModifyTime()));
    }

    @Test
    void findPageByDateBetweenAndSerialNo() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(100)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        Page<UsageTimeInfoEntity> usageTimeInfoEntityList
                = usageTimeInfoRepository.findPageByDateBetweenAndSerialNo(PageRequest.of(0, 100), LocalDate.now(), LocalDate.now(), usageTimeInfoEntity.getSerialNo());

        List<UsageTimeInfoEntity> usageTimeInfoEntities = usageTimeInfoEntityList.getContent();

        assertThat(usageTimeInfoEntities.get(0).getSerialNo(), is(usageTimeInfoEntity.getSerialNo()));
        assertThat(usageTimeInfoEntities.get(0).getDate(), is(usageTimeInfoEntity.getDate()));
        assertThat(usageTimeInfoEntities.get(0).getCustNm(), is(usageTimeInfoEntity.getCustNm()));
        assertThat(usageTimeInfoEntities.get(0).getMinuteOfUsageTime(), is(usageTimeInfoEntity.getMinuteOfUsageTime()));
        assertThat(usageTimeInfoEntities.get(0).getCreateTime(), is(usageTimeInfoEntity.getCreateTime()));
        assertThat(usageTimeInfoEntities.get(0).getModifyTime(), is(usageTimeInfoEntity.getModifyTime()));
    }

    @Test
    void findByDateAndSerialNo() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(100)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        UsageTimeInfoEntity usageTimeInfoEntity1
                = usageTimeInfoRepository.findByDateAndSerialNo(LocalDate.now(), usageTimeInfoEntity.getSerialNo());

        assertThat(usageTimeInfoEntity1.getSerialNo(), is(usageTimeInfoEntity.getSerialNo()));
        assertThat(usageTimeInfoEntity1.getDate(), is(usageTimeInfoEntity.getDate()));
        assertThat(usageTimeInfoEntity1.getCustNm(), is(usageTimeInfoEntity.getCustNm()));
        assertThat(usageTimeInfoEntity1.getMinuteOfUsageTime(), is(usageTimeInfoEntity.getMinuteOfUsageTime()));
        assertThat(usageTimeInfoEntity1.getCreateTime(), is(usageTimeInfoEntity.getCreateTime()));
        assertThat(usageTimeInfoEntity1.getModifyTime(), is(usageTimeInfoEntity.getModifyTime()));
    }

    @Test
    void findAllUsageTimeSummaryByDate() {
        for (int i = 0; i < 10; i++) {
            UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                    .serialNo("serialNo_" + i)
                    .date(LocalDate.now())
                    .custNm("custNm_" + i)
                    .minuteOfUsageTime(i + 100)
                    .createTime(LocalDateTime.now())
                    .modifyTime(LocalDateTime.now())
                    .build();

            UsageTimeInfoEntity usageTimeInfoEntity1 = usageTimeInfoRepository.save(usageTimeInfoEntity);
        }

        List<AllDateSummaryInfoDto> allDateSummaryInfoDtos
                = usageTimeInfoRepository.findAllUsageTimeSummaryByDate(LocalDate.now(), LocalDate.now());

        assertThat(allDateSummaryInfoDtos.get(0).getMinuteOfUsageTime(), is(1045L));
    }

    @Test
    void findAllUsageTimeSummaryBySerialNo() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(1000)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        List<AllDateSummaryInfoDto> allDateSummaryInfoDtos
                = usageTimeInfoRepository.findAllUsageTimeSummaryBySerialNo(usageTimeInfoEntity.getSerialNo(), LocalDate.now(), LocalDate.now());

        assertThat(allDateSummaryInfoDtos.get(0).getMinuteOfUsageTime(), is(1000L));
    }

    @Test
    void findGroupByCustNmAndSerialNo() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(1000)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        List<UsageTimeInfoCustNameGroupInfoDto> usageTimeInfoCustNameGroupInfoDtos
                = usageTimeInfoRepository.findGroupByCustNmAndSerialNo();

        assertThat(usageTimeInfoCustNameGroupInfoDtos.get(0).getCustNm(), is("custNm_0"));
        assertThat(usageTimeInfoCustNameGroupInfoDtos.get(0).getSerialNo(), is("serialNo_0"));
    }

    @Test
    void findAllUsageTimeGroupbyStartDateAndEndDate() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(1000)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        List<DailySummaryInfoDto> dailySummaryInfoDtos
                = usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDate(LocalDate.now(), LocalDate.now());

        assertThat(dailySummaryInfoDtos.get(0).getDate(), is(LocalDate.now()));
        assertThat(dailySummaryInfoDtos.get(0).getMinuteOfUsageTime(), is(1000L));
    }

    @Test
    void findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(1000)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        List<DailySummaryInfoDto> dailySummaryInfoDtos
                = usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDateAndSerialNo(LocalDate.now(), LocalDate.now(), usageTimeInfoEntity.getSerialNo());

        assertThat(dailySummaryInfoDtos.get(0).getDate(), is(LocalDate.now()));
        assertThat(dailySummaryInfoDtos.get(0).getMinuteOfUsageTime(), is(1000L));
    }

    @Test
    void findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm() {
        UsageTimeInfoEntity usageTimeInfoEntity = UsageTimeInfoEntity.builder()
                .serialNo("serialNo_0")
                .date(LocalDate.now())
                .custNm("custNm_0")
                .minuteOfUsageTime(1000)
                .createTime(LocalDateTime.now().withNano(0))
                .modifyTime(LocalDateTime.now().withNano(0))
                .build();

        usageTimeInfoRepository.save(usageTimeInfoEntity);

        List<DailySummaryInfoDto> dailySummaryInfoDtos
                = usageTimeInfoRepository.findAllUsageTimeGroupbyStartDateAndEndDateAndCustNm(LocalDate.now(), LocalDate.now(), usageTimeInfoEntity.getCustNm());

        assertThat(dailySummaryInfoDtos.get(0).getDate(), is(LocalDate.now()));
        assertThat(dailySummaryInfoDtos.get(0).getMinuteOfUsageTime(), is(1000L));
    }
}