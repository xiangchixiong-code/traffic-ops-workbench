package com.xianggon.trafficops.repository;

import com.xianggon.trafficops.domain.OperationLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

    List<OperationLog> findTop20ByOrderByCreatedAtDesc();
}
