package com.penta.ps.license.license.repositoryTest;

import com.penta.ps.license.license.entity.EventLogEntity;
import com.penta.ps.license.license.entity.PolicyLogEntity;
import com.penta.ps.license.license.entity.SystemLogEntity;
import com.penta.ps.license.license.repository.EventLogRepository;
import com.penta.ps.license.license.repository.PolicyLogRepository;
import com.penta.ps.license.license.repository.SystemLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LogRepositoryTest {
    @Autowired
    private SystemLogRepository systemLogRepository;

    @Autowired
    private PolicyLogRepository policyLogRepository;

    @Autowired
    private EventLogRepository eventLogRepository;

    @Test
    void getSystemLogAll() {
        Page<SystemLogEntity> systemLogList = systemLogRepository.findByLogLevelAndCreateTimeBetween(0,
                Timestamp.valueOf(LocalDateTime.MIN),
                Timestamp.valueOf(LocalDateTime.now()),
                PageRequest.of(0, 5));

        then(!systemLogList.isEmpty());

        for (SystemLogEntity sle : systemLogList) {
            then(sle.getLogLevel()).isBetween(0, 2);
            then(sle.getFacility()).isBetween(0, 4);
        }
    }

    @Test
    void getPolicyLogAll() {
        Page<PolicyLogEntity> policyLogList = policyLogRepository.findByLogLevelAndCreateTimeBetween(0,
                Timestamp.valueOf(LocalDateTime.MIN),
                Timestamp.valueOf(LocalDateTime.now()),
                PageRequest.of(0, 5));

        then(!policyLogList.isEmpty());

        for (PolicyLogEntity sle : policyLogList) {
            then(sle.getLogLevel()).isBetween(0, 2);
            then(sle.getFacility()).isBetween(0, 4);
        }
    }

    @Test
    void getEventLogAll() {
        Page<EventLogEntity> eventLogList = eventLogRepository.findByLogLevelAndCreateTimeBetween(0,
                Timestamp.valueOf(LocalDateTime.MIN),
                Timestamp.valueOf(LocalDateTime.now()),
                PageRequest.of(0, 5));

        then(!eventLogList.isEmpty());

        for (EventLogEntity sle : eventLogList) {
            then(sle.getLogLevel()).isBetween(0, 2);
            then(sle.getFacility()).isBetween(0, 4);
        }
    }
}

