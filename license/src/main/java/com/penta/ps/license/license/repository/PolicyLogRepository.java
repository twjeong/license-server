package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.PolicyLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;


public interface PolicyLogRepository extends JpaRepository<PolicyLogEntity, Timestamp> {
    Page<PolicyLogEntity> findByCreateTimeBetween(Timestamp createTimeBegin,
                                                  Timestamp createTimeEnd,
                                                  Pageable pageable);

    Page<PolicyLogEntity> findByLogLevelAndCreateTimeBetween(int logLevel,
                                                             Timestamp createTimeBegin,
                                                             Timestamp createTimeEnd,
                                                             Pageable pageable);

    Page<PolicyLogEntity> findByMsgContainingAndCreateTimeBetween(String msg,
                                                                  Timestamp createTimeBegin,
                                                                  Timestamp createTimeEnd,
                                                                  Pageable pageable);

    Page<PolicyLogEntity> findByLogLevelOrMsgContainingAndCreateTimeBetween(int logLevel,
                                                                            String msg,
                                                                            Timestamp createTimeBegin,
                                                                            Timestamp createTimeEnd,
                                                                            Pageable pageable);

}
