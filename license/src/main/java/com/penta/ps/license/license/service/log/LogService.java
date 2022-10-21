package com.penta.ps.license.license.service.log;

import com.penta.ps.license.license.dto.log.*;
import com.penta.ps.license.license.entity.*;
import com.penta.ps.license.license.repository.EventLogRepository;
import com.penta.ps.license.license.repository.PolicyLogRepository;
import com.penta.ps.license.license.repository.SystemLogRepository;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.ManagerType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService implements ApplicationListener<ApplicationReadyEvent> {

    private final SystemLogRepository systemLogRepository;
    private final PolicyLogRepository policyLogRepository;
    private final EventLogRepository eventLogRepository;
    private final SchedulerService schedulerService;
    private final LogsBackup logsBackup;

    private int paramCombination;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        LogsBackupDto logsBackupDto = logsBackup.load();
        if (logsBackupDto.getEnable()) {
            setScheduledBackup(logsBackupDto);
        }
    }

    @Transactional
    public Page<SystemLogEntity> getSystemLogList(Boolean flagFilter,
                                                  Integer logLevel,
                                                  String msg,
                                                  Timestamp createTimeBegin,
                                                  Timestamp createTimeEnd,
                                                  Integer currentPageNo,
                                                  Integer recordsPerPage,
                                                  String sortColumn,
                                                  Boolean flagSortAsc) {

        Page<SystemLogEntity> systemLogEntities = null;
        paramCombination = 0;

        if (flagFilter) {
            if (logLevel != null) paramCombination += 1;
            if (msg != null) paramCombination += 2;
        }

        switch (paramCombination) {
            case 0:
                if (flagSortAsc) {
                    systemLogEntities = systemLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    systemLogEntities = systemLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }
                break;
            case 1:
                if (flagSortAsc) {
                    systemLogEntities = systemLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    systemLogEntities = systemLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 2:
                if (flagSortAsc) {
                    systemLogEntities = systemLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    systemLogEntities = systemLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 3:
                if (flagSortAsc) {
                    systemLogEntities = systemLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    systemLogEntities = systemLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            default:
                break;
        }

        return systemLogEntities;
    }

    @Transactional
    public Page<PolicyLogEntity> getPolicyLogList(Boolean flagFilter,
                                                  Integer logLevel,
                                                  String msg,
                                                  Timestamp createTimeBegin,
                                                  Timestamp createTimeEnd,
                                                  Integer currentPageNo,
                                                  Integer recordsPerPage,
                                                  String sortColumn,
                                                  Boolean flagSortAsc) {

        Page<PolicyLogEntity> policyLogEntities = null;
        paramCombination = 0;

        if (flagFilter) {
            if (logLevel != null) paramCombination += 1;
            if (msg != null) paramCombination += 2;
        }

        switch (paramCombination) {
            case 0:
                if (flagSortAsc) {
                    policyLogEntities = policyLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    policyLogEntities = policyLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }
                break;
            case 1:
                if (flagSortAsc) {
                    policyLogEntities = policyLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    policyLogEntities = policyLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 2:
                if (flagSortAsc) {
                    policyLogEntities = policyLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    policyLogEntities = policyLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 3:
                if (flagSortAsc) {
                    policyLogEntities = policyLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    policyLogEntities = policyLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            default:
                break;
        }

        return policyLogEntities;
    }

    @Transactional
    public Page<EventLogEntity> getEventLogList(Boolean flagFilter,
                                                Integer logLevel,
                                                String msg,
                                                Timestamp createTimeBegin,
                                                Timestamp createTimeEnd,
                                                Integer currentPageNo,
                                                Integer recordsPerPage,
                                                String sortColumn,
                                                Boolean flagSortAsc) {

        Page<EventLogEntity> eventLogEntities = null;

        paramCombination = 0;

        if (flagFilter) {
            if (logLevel != null) paramCombination += 1;
            if (msg != null) paramCombination += 2;
        }

        switch (paramCombination) {
            case 0:
                if (flagSortAsc) {
                    eventLogEntities = eventLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    eventLogEntities = eventLogRepository.findByCreateTimeBetween(createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }
                break;
            case 1:
                if (flagSortAsc) {
                    eventLogEntities = eventLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    eventLogEntities = eventLogRepository.findByLogLevelAndCreateTimeBetween(logLevel,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 2:
                if (flagSortAsc) {
                    eventLogEntities = eventLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    eventLogEntities = eventLogRepository.findByMsgContainingAndCreateTimeBetween(msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            case 3:
                if (flagSortAsc) {
                    eventLogEntities = eventLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).ascending()));
                } else {
                    eventLogEntities = eventLogRepository.findByLogLevelOrMsgContainingAndCreateTimeBetween(logLevel,
                            msg,
                            createTimeBegin,
                            createTimeEnd,
                            PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(sortColumn).descending()));
                }

                break;

            default:
                break;
        }

        return eventLogEntities;
    }

    @Transactional
    public void saveSystemLog(SystemLogDto systemLogDto) {
        systemLogRepository.save(systemLogDto.toEntity());
    }

    @Transactional
    public void savePolicyLog(PolicyLogDto policyLogDto) {
        policyLogRepository.save(policyLogDto.toEntity());
    }

    @Transactional
    public void saveEventLog(EventLogDto eventLogDto) {
        eventLogRepository.save(eventLogDto.toEntity());
    }

    @Transactional
    public LogsBackupDto getLogsBackup() throws IOException {

        return logsBackup.load();
    }

    @Transactional
    public void setLogsBackup(LogsBackupDto req) throws IOException, InterruptedException {

        if (req.getForce()) {
            try {
                logsBackup.backup();

                eventLogRepository.deleteAll();
                systemLogRepository.deleteAll();
                policyLogRepository.deleteAll();

                eventLogRepository.save(EventLogDto.builder()
                        .logLevel(LogLevelType.INFO)
                        .facility(ManagerType.LOG)
                        .msg("[Log] Backup is complete.")
                        .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                        .build().toEntity());
            } catch (Exception e) {
                eventLogRepository.save(EventLogDto.builder()
                        .logLevel(LogLevelType.ERROR)
                        .facility(ManagerType.LOG)
                        .msg("[Log] Backup is failed.")
                        .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                        .build().toEntity());

                throw e;
            }
        } else {
            logsBackup.save(req);

            if (req.getEnable()) {
                setScheduledBackup(req);
            } else {
                schedulerService.stop();
            }
        }
    }

    private void setScheduledBackup(LogsBackupDto req) {

        schedulerService.start(() -> {
            try {
                logsBackup.backup();
            } catch (Exception e) {
                log.error("[Restore database] failed:{}", e.getMessage());
                eventLogRepository.save(EventLogDto.builder()
                        .logLevel(LogLevelType.ERROR)
                        .facility(ManagerType.LOG)
                        .msg("[Log] Scheduled backup is failed.")
                        .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                        .build().toEntity());
            }

            eventLogRepository.deleteAll();
            systemLogRepository.deleteAll();
            policyLogRepository.deleteAll();

            eventLogRepository.save(EventLogDto.builder()
                    .logLevel(LogLevelType.INFO)
                    .facility(ManagerType.LOG)
                    .msg("[Log] Scheduled backup is complete.")
                    .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                    .build().toEntity());

        }, "0 " +
                req.getMinute().toString() + " " +
                req.getHour().toString() + " " +
                req.getDay().toString() + " " +
                req.getMonth() +
                " *");
    }

    @Transactional
    public void setLogsRestore(LogsRestoreDto req) throws IOException, InterruptedException {
        try {
            logsBackup.restore(req.getFileName());

            eventLogRepository.save(EventLogDto.builder()
                    .logLevel(LogLevelType.INFO)
                    .facility(ManagerType.LOG)
                    .msg("[Log] Restoration is complete.")
                    .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                    .build().toEntity());

        } catch (Exception e) {

            eventLogRepository.save(EventLogDto.builder()
                    .logLevel(LogLevelType.ERROR)
                    .facility(ManagerType.LOG)
                    .msg("[Log] Restoration is failed.")
                    .createTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()))
                    .build().toEntity());

            throw e;
        }
    }

    public List<LogsRestoreDto> getBackupFileList() {

        return logsBackup.backupFileList();
    }
}
