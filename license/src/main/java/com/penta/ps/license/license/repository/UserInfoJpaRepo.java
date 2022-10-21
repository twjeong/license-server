package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoJpaRepo extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findById(String id);
}
