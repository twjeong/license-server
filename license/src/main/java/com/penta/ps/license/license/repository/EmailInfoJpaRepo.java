package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.EmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailInfoJpaRepo extends JpaRepository<EmailInfo, Long> {
    @Query(value="select m from EmailInfo m")
    List<EmailInfo> findAllById();
}
