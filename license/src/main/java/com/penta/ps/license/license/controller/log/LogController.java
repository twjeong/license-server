package com.penta.ps.license.license.controller.log;

import com.penta.ps.license.license.dto.log.*;
import com.penta.ps.license.license.entity.EventLogEntity;
import com.penta.ps.license.license.entity.PolicyLogEntity;
import com.penta.ps.license.license.entity.SystemLogEntity;
import com.penta.ps.license.license.response.*;
import com.penta.ps.license.license.service.log.LogService;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    private final int BLOCK_PAGE_NUM_COUNT = 10;

    @GetMapping(path = "/system-logs", produces = "application/json")
    public ResponseEntity<ResponseSystemLogMessage> getSystemLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                                 @RequestParam("records-per-page") Integer recordsPerPage,
                                                                 @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                                 @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                                 @RequestParam(value = "sort-column") String sortColumn,
                                                                 @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<SystemLogEntity> systemLogEntities = logService.getSystemLogList(
                false, 0, null,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<SystemLogDto> systemLogDtoList = new ArrayList<>();
        for (SystemLogEntity systemLogEntity : systemLogEntities) {
            SystemLogDto systemLogDto = SystemLogDto.builder()
                    .facility(ManagerType.values()[systemLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[systemLogEntity.getLogLevel()])
                    .msg(systemLogEntity.getMsg())
                    .createTime(systemLogEntity.getCreateTime())
                    .build();

            systemLogDtoList.add(systemLogDto);
        }

        ResponseSystemLogMessage resLogMsg = ResponseSystemLogMessage.builder()
                .result(0)
                .msg("Success")
                .systemLogList(systemLogDtoList)
                .pageTotalSize(systemLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponseSystemLogMessage>(resLogMsg, HttpStatus.OK);
    }

    @GetMapping(path = "/specific-system-logs", produces = "application/json")
    public ResponseEntity<ResponseSystemLogMessage> getSpecificSystemLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                                         @RequestParam("records-per-page") Integer recordsPerPage,
                                                                         @RequestParam(value = "log-level", required = false) Integer logLevel,
                                                                         @RequestParam(value = "msg", required = false) String msg,
                                                                         @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                                         @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                                         @RequestParam(value = "sort-column") String sortColumn,
                                                                         @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<SystemLogEntity> systemLogEntities = logService.getSystemLogList(
                true,
                logLevel,
                msg,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<SystemLogDto> systemLogDtoList = new ArrayList<>();
        for (SystemLogEntity systemLogEntity : systemLogEntities) {
            SystemLogDto systemLogDto = SystemLogDto.builder()
                    .facility(ManagerType.values()[systemLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[systemLogEntity.getLogLevel()])
                    .msg(systemLogEntity.getMsg())
                    .createTime(systemLogEntity.getCreateTime())
                    .build();

            systemLogDtoList.add(systemLogDto);
        }

        ResponseSystemLogMessage resLogMsg = ResponseSystemLogMessage.builder()
                .result(0)
                .msg("Success")
                .systemLogList(systemLogDtoList)
                .pageTotalSize(systemLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponseSystemLogMessage>(resLogMsg, HttpStatus.OK);
    }


    @GetMapping(path = "/policy-logs", produces = "application/json")
    public ResponseEntity<ResponsePolicyLogMessage> getPolicyLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                                 @RequestParam("records-per-page") Integer recordsPerPage,
                                                                 @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                                 @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                                 @RequestParam(value = "sort-column") String sortColumn,
                                                                 @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<PolicyLogEntity> policyLogEntities = logService.getPolicyLogList(
                false, 0, null,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<PolicyLogDto> policyLogDtoList = new ArrayList<>();
        for (PolicyLogEntity policyLogEntity : policyLogEntities) {
            PolicyLogDto policyLogDto = PolicyLogDto.builder()
                    .facility(ManagerType.values()[policyLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[policyLogEntity.getLogLevel()])
                    .msg(policyLogEntity.getMsg())
                    .createTime(policyLogEntity.getCreateTime())
                    .build();

            policyLogDtoList.add(policyLogDto);
        }

        ResponsePolicyLogMessage resLogMsg = ResponsePolicyLogMessage.builder()
                .result(0)
                .msg("Success")
                .policyLogList(policyLogDtoList)
                .pageTotalSize(policyLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponsePolicyLogMessage>(resLogMsg, HttpStatus.OK);
    }

    @GetMapping(path = "/specific-policy-logs", produces = "application/json")
    public ResponseEntity<ResponsePolicyLogMessage> getSpecificPolicyLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                                         @RequestParam("records-per-page") Integer recordsPerPage,
                                                                         @RequestParam(value = "log-level", required = false) Integer logLevel,
                                                                         @RequestParam(value = "msg", required = false) String msg,
                                                                         @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                                         @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                                         @RequestParam(value = "sort-column") String sortColumn,
                                                                         @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<PolicyLogEntity> policyLogEntities = logService.getPolicyLogList(
                true,
                logLevel,
                msg,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<PolicyLogDto> policyLogDtoList = new ArrayList<>();
        for (PolicyLogEntity policyLogEntity : policyLogEntities) {
            PolicyLogDto policyLogDto = PolicyLogDto.builder()
                    .facility(ManagerType.values()[policyLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[policyLogEntity.getLogLevel()])
                    .msg(policyLogEntity.getMsg())
                    .createTime(policyLogEntity.getCreateTime())
                    .build();

            policyLogDtoList.add(policyLogDto);
        }

        ResponsePolicyLogMessage resLogMsg = ResponsePolicyLogMessage.builder()
                .result(0)
                .msg("Success")
                .policyLogList(policyLogDtoList)
                .pageTotalSize(policyLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponsePolicyLogMessage>(resLogMsg, HttpStatus.OK);
    }


    @GetMapping(path = "/event-logs", produces = "application/json")
    public ResponseEntity<ResponseEventLogMessage> getEventLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                               @RequestParam("records-per-page") Integer recordsPerPage,
                                                               @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                               @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                               @RequestParam(value = "sort-column") String sortColumn,
                                                               @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc,
                                                               @RequestParam(value = "page-info") Boolean pageInfo) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<EventLogEntity> eventLogEntities = logService.getEventLogList(
                false, 0, null,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<EventLogDto> eventLogDtoList = new ArrayList<>();
        for (EventLogEntity eventLogEntity : eventLogEntities) {
            EventLogDto eventLogDto = EventLogDto.builder()
                    .facility(ManagerType.values()[eventLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[eventLogEntity.getLogLevel()])
                    .msg(eventLogEntity.getMsg())
                    .createTime(eventLogEntity.getCreateTime())
                    .build();

            eventLogDtoList.add(eventLogDto);
        }

        ResponseEventLogMessage resLogMsg = ResponseEventLogMessage.builder()
                .result(0)
                .msg("Success")
                .eventLogList(eventLogDtoList)
                .pageTotalSize(eventLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponseEventLogMessage>(resLogMsg, HttpStatus.OK);
    }

    @GetMapping(path = "/specific-event-logs", produces = "application/json")
    public ResponseEntity<ResponseEventLogMessage> getSpecificEventLog(@RequestParam(value = "current-page-no", defaultValue = "1") Integer currentPageNo,
                                                                       @RequestParam("records-per-page") Integer recordsPerPage,
                                                                       @RequestParam(value = "log-level", required = false) Integer logLevel,
                                                                       @RequestParam(value = "msg", required = false) String msg,
                                                                       @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                                                       @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                                                       @RequestParam(value = "sort-column") String sortColumn,
                                                                       @RequestParam(value = "flag-sort-asc") Boolean flagSortAsc) {

        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        Page<EventLogEntity> eventLogEntities = logService.getEventLogList(
                true,
                logLevel,
                msg,
                createTimeBegin,
                createTimeEnd,
                currentPageNo,
                recordsPerPage,
                sortColumn,
                flagSortAsc);

        List<EventLogDto> eventLogDtoList = new ArrayList<>();
        for (EventLogEntity eventLogEntity : eventLogEntities) {
            EventLogDto eventLogDto = EventLogDto.builder()
                    .facility(ManagerType.values()[eventLogEntity.getFacility()])
                    .logLevel(LogLevelType.values()[eventLogEntity.getLogLevel()])
                    .msg(eventLogEntity.getMsg())
                    .createTime(eventLogEntity.getCreateTime())
                    .build();

            eventLogDtoList.add(eventLogDto);
        }

        ResponseEventLogMessage resLogMsg = ResponseEventLogMessage.builder()
                .result(0)
                .msg("Success")
                .eventLogList(eventLogDtoList)
                .pageTotalSize(eventLogEntities.getTotalPages())
                .build();

        return new ResponseEntity<ResponseEventLogMessage>(resLogMsg, HttpStatus.OK);
    }

    @GetMapping(value = "/logs", produces = "application/json")
    public ResponseEntity<ResponseLog> getLogs(@RequestParam(value = "log-type") String logType,
                                              @RequestParam(value = "page", defaultValue = "1") Integer curPageNum,
                                              @RequestParam(value = "log-level", required = false) String logLevel,
                                              @RequestParam(value = "msg", required = false) String msg,
                                              @RequestParam(value = "create-time-begin", required = false) Timestamp createTimeBegin,
                                              @RequestParam(value = "create-time-end", required = false) Timestamp createTimeEnd,
                                              @RequestParam(value = "sort-column", required = false) String sortColumn,
                                              @RequestParam(value = "flag-sort-asc", required = false) Boolean flagSortAsc) {

        Boolean flagFilter = logLevel != null || msg != null;
        int indexLogLevel = 0;

        List<LogDto> logDtoList = new ArrayList<>();

        int prevPageNum = 0, nextPageNum = 0, lastPageNum = 0;

        if (logLevel != null) indexLogLevel = LogLevelType.valueOf(logLevel).ordinal();
        if (createTimeBegin == null) createTimeBegin = Timestamp.valueOf(LocalDateTime.MIN);
        if (createTimeEnd == null) createTimeEnd = Timestamp.valueOf(LocalDateTime.now());

        if (logType.equals("system")) {
            Page<SystemLogEntity> systemLogEntities = logService.getSystemLogList(flagFilter, indexLogLevel, msg, createTimeBegin, createTimeEnd, curPageNum, BLOCK_PAGE_NUM_COUNT, sortColumn, flagSortAsc);

            for (SystemLogEntity systemLogEntity : systemLogEntities) {
                LogDto logDto = LogDto.builder()
                        .facility(ManagerType.values()[systemLogEntity.getFacility()])
                        .logLevel(LogLevelType.values()[systemLogEntity.getLogLevel()])
                        .msg(systemLogEntity.getMsg())
                        .createTime(systemLogEntity.getCreateTime())
                        .build();

                logDtoList.add(logDto);
            }
            lastPageNum = systemLogEntities.getTotalPages();

        } else if (logType.equals("policy")) {
            Page<PolicyLogEntity> policyLogEntities = logService.getPolicyLogList(flagFilter, indexLogLevel, msg, createTimeBegin, createTimeEnd, curPageNum, BLOCK_PAGE_NUM_COUNT, sortColumn, flagSortAsc);

            for (PolicyLogEntity policyLogEntity : policyLogEntities) {
                LogDto logDto = LogDto.builder()
                        .facility(ManagerType.values()[policyLogEntity.getFacility()])
                        .logLevel(LogLevelType.values()[policyLogEntity.getLogLevel()])
                        .msg(policyLogEntity.getMsg())
                        .createTime(policyLogEntity.getCreateTime())
                        .build();

                logDtoList.add(logDto);
            }
            lastPageNum = policyLogEntities.getTotalPages();

        } else {
            Page<EventLogEntity> eventLogEntities = logService.getEventLogList(flagFilter, indexLogLevel, msg, createTimeBegin, createTimeEnd, curPageNum, BLOCK_PAGE_NUM_COUNT, sortColumn, flagSortAsc);

            for (EventLogEntity eventLogEntity : eventLogEntities) {
                LogDto logDto = LogDto.builder()
                        .facility(ManagerType.values()[eventLogEntity.getFacility()])
                        .logLevel(LogLevelType.values()[eventLogEntity.getLogLevel()])
                        .msg(eventLogEntity.getMsg())
                        .createTime(eventLogEntity.getCreateTime())
                        .build();

                logDtoList.add(logDto);
            }
            lastPageNum = eventLogEntities.getTotalPages();
        }

        prevPageNum = curPageNum - 1;
        if (prevPageNum < 1) prevPageNum = 1;
        nextPageNum = curPageNum + 1;
        if (nextPageNum > lastPageNum) nextPageNum = lastPageNum;

        Integer divStartPageNum = curPageNum / BLOCK_PAGE_NUM_COUNT;
        Integer modStartpageNum = curPageNum % BLOCK_PAGE_NUM_COUNT;

        Integer startPageNum = (divStartPageNum * BLOCK_PAGE_NUM_COUNT) + 1;
        if (modStartpageNum == 0) {
            startPageNum -= BLOCK_PAGE_NUM_COUNT;
        }

        List<Integer> pageList = new ArrayList<Integer>(lastPageNum);
        for (int val = startPageNum, idx = 0; idx < BLOCK_PAGE_NUM_COUNT && val <= lastPageNum; val++, idx++) {
            pageList.add(idx, val);
        }

        ResponseLog responseSystemLog = ResponseLog.builder()
                .logList(logDtoList.toArray(new LogDto[logDtoList.size()]))
                .pageList(pageList)
                .prevPageNum(prevPageNum)
                .curPageNum(curPageNum)
                .nextPageNum(nextPageNum)
                .lastPageNum(lastPageNum)
                .build();

        return new ResponseEntity<>(responseSystemLog, HttpStatus.OK);
    }

    @PostMapping(path = "/system-logs", produces = "application/json")
    public ResponseEntity<ResponseMessage> writeSystemLog(@RequestBody SystemLogDto systemLogDto) {
        logService.saveSystemLog(systemLogDto);

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @PostMapping(path = "/policy-logs", produces = "application/json")
    public ResponseEntity<ResponseMessage> writePolicyLog(@RequestBody PolicyLogDto policyLogDto) {
        logService.savePolicyLog(policyLogDto);

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @PostMapping(path = "/event-logs", produces = "application/json")
    public ResponseEntity<ResponseMessage> writeEventLog(@RequestBody EventLogDto eventLogDto) {
        logService.saveEventLog(eventLogDto);

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @GetMapping(value = "/logs-backup")
    public ResponseEntity<ResponseLogsBackupMessage> getLogsBackup() {

        ResponseLogsBackupMessage resLogsBackupMsg;

        try {
            LogsBackupDto logsBackupDto = logService.getLogsBackup();

            resLogsBackupMsg = ResponseLogsBackupMessage.builder()
                    .result(0)
                    .msg("Success")
                    .minute(logsBackupDto.getMinute())
                    .hour(logsBackupDto.getHour())
                    .day(logsBackupDto.getDay())
                    .month(logsBackupDto.getMonth())
                    .enable(logsBackupDto.getEnable())
                    .path(logsBackupDto.getPath())
                    .build();

        } catch (IOException e) {

            resLogsBackupMsg = ResponseLogsBackupMessage.builder()
                    .result(1)
                    .msg(e.getMessage())
                    .minute(0)
                    .hour(0)
                    .day(0)
                    .month("")
                    .enable(false)
                    .path("")
                    .build();
        }

        return new ResponseEntity<ResponseLogsBackupMessage>(resLogsBackupMsg,
                HttpStatus.OK);
    }

    @PutMapping(value = "/logs-backup")
    public ResponseEntity<ResponseMessage> setLogsBackup(@RequestBody LogsBackupDto req) {

        try {
            logService.setLogsBackup(req);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .result(0)
                    .msg("Success")
                    .build(),
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(ResponseMessage.builder()
                    .result(1)
                    .msg(e.getMessage())
                    .build(),
                    HttpStatus.OK);
        }
    }

    @PutMapping(value = "/logs-restore")
    public ResponseEntity<ResponseMessage> setLogsRestore(@RequestBody LogsRestoreDto req) {

        try {
            logService.setLogsRestore(req);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .result(0)
                    .msg("Success")
                    .build(),
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(ResponseMessage.builder()
                    .result(1)
                    .msg(e.getMessage())
                    .build(),
                    HttpStatus.OK);
        }
    }

    @GetMapping(value = "/logs-restore")
    public ResponseEntity<ResponseLogsRestoreMessage> getLogsRestore() {

        ResponseLogsRestoreMessage responseLogsRestoreMessage = ResponseLogsRestoreMessage.builder()
                .result(0)
                .msg("Success")
                .fileList(logService.getBackupFileList())
                .build();

        return new ResponseEntity<ResponseLogsRestoreMessage>(responseLogsRestoreMessage,
                HttpStatus.OK);
    }
}
