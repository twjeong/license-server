package com.penta.ps.license.license.service;

import com.penta.ps.license.license.dto.log.LogsBackupDto;
import com.penta.ps.license.license.dto.log.LogsRestoreDto;
import com.penta.ps.license.license.service.log.LogService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        /*"spring.datasource.url=jdbc:mysql://10.0.69.59:3306?characterEncoding=UTF-8&serverTimezone=Asia/Seoul&sendStringParametersAsUnicode=false",*/
        "storage-ip=127.0.0.1"
})
public class LogServiceTest {
    @Autowired
    private LogService logService;

    @Test
    public void logsBackupForceTest() throws IOException, InterruptedException {
        //given

        //when
        logService.setLogsBackup(LogsBackupDto.builder()
                .force(true)
                .build());

        //then
    }

    @Test
    public void logsBackupScheduleTest() throws IOException, InterruptedException {
        //given

        //when
        logService.setLogsBackup(LogsBackupDto.builder()
                .minute(49)
                .hour(15)
                .day(8)
                .month("6")
                .enable(true)
                .force(false)
                .build());

        //then
        Thread.sleep(120000);
    }


    @Test
    public void logsBackupSetTest() throws IOException, InterruptedException {
        //given
        logService.setLogsBackup(LogsBackupDto.builder()
                .minute(0)
                .hour(0)
                .day(0)
                .month("")
                .enable(false)
                .force(false)
                .build());

        //when
        logService.setLogsBackup(LogsBackupDto.builder()
                .minute(30)
                .hour(18)
                .day(18)
                .month("3,6,9,12")
                .enable(false)
                .force(false)
                .build());

        //then
        LogsBackupDto logsBackupDto = logService.getLogsBackup();

        assertThat(logsBackupDto.getMinute()).isEqualTo(30);
        assertThat(logsBackupDto.getHour()).isEqualTo(18);
        assertThat(logsBackupDto.getDay()).isEqualTo(18);
        assertThat(logsBackupDto.getMonth()).isEqualTo("3,6,9,12");
    }

    @Test
    public void logsRestoreTest() throws IOException, InterruptedException {

        logService.setLogsRestore(LogsRestoreDto.builder()
                .fileName("backup_logs_20210727100051")
                .build());
    }

    /*
    @Test
    public void getSystemLog() {

        String expectResult = "{\n" +
                "\"result\": 0,\n" +
                "\"msg\": \"Success\",\n" +
                "\"systemLogList\":[\n" +
                "{\"facility\": \"UI\", \"logLevel\": \"INFO\", \"msg\": \"2020-07-13 TEST\", \"createTime\": \"2020-07-13T02:39:23.000+00:00\"…},\n" +
                "{\"facility\": \"CONTRACT\", \"logLevel\": \"ERROR\", \"msg\": \"Connect\", \"createTime\": \"2020-07-13T07:49:34.000+00:00\"…},\n" +
                "{\"facility\": \"CONTRACT\", \"logLevel\": \"ERROR\", \"msg\": \"2020-07-15 TEST\", \"createTime\": \"2020-07-15T00:32:48.000+00:00\"…}\n" +
                "],\n" +
                "\"pageTotalSize\": 3\n" +
                "}";

        mockServer.expect(requestTo("http://localhost:8082/system-logs?records-per-page=5"))
                .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

        List<SystemLogDto> systemLogDtoList = logService.getSystemLogList(null,
                null,
                null,
                null,
                1,
                5);

        for (SystemLogDto sld : systemLogDtoList) {
            then("UI").isEqualTo(sld.getFacility());
            then("INFO").isEqualTo(sld.getLogLevel());
        }

    }*/
}
