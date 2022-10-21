package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.SystemLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;


public interface SystemLogRepository extends JpaRepository<SystemLogEntity, Timestamp> {
    Page<SystemLogEntity> findByCreateTimeBetween(Timestamp createTimeBegin,
                                                  Timestamp createTimeEnd,
                                                  Pageable pageable);

    Page<SystemLogEntity> findByLogLevelAndCreateTimeBetween(int logLevel,
                                                             Timestamp createTimeBegin,
                                                             Timestamp createTimeEnd,
                                                             Pageable pageable);

    Page<SystemLogEntity> findByMsgContainingAndCreateTimeBetween(String msg,
                                                                  Timestamp createTimeBegin,
                                                                  Timestamp createTimeEnd,
                                                                  Pageable pageable);

    Page<SystemLogEntity> findByLogLevelOrMsgContainingAndCreateTimeBetween(int logLevel,
                                                                            String msg,
                                                                            Timestamp createTimeBegin,
                                                                            Timestamp createTimeEnd,
                                                                            Pageable pageable);

}
