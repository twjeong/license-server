package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.EventLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;


public interface EventLogRepository extends JpaRepository<EventLogEntity, Timestamp> {
    Page<EventLogEntity> findByCreateTimeBetween(Timestamp createTimeBegin,
                                                 Timestamp createTimeEnd,
                                                 Pageable pageable);

    Page<EventLogEntity> findByLogLevelAndCreateTimeBetween(int logLevel,
                                                            Timestamp createTimeBegin,
                                                            Timestamp createTimeEnd,
                                                            Pageable pageable);

    Page<EventLogEntity> findByMsgContainingAndCreateTimeBetween(String msg,
                                                                 Timestamp createTimeBegin,
                                                                 Timestamp createTimeEnd,
                                                                 Pageable pageable);

    Page<EventLogEntity> findByLogLevelOrMsgContainingAndCreateTimeBetween(int logLevel,
                                                                           String msg,
                                                                           Timestamp createTimeBegin,
                                                                           Timestamp createTimeEnd,
                                                                           Pageable pageable);


}
