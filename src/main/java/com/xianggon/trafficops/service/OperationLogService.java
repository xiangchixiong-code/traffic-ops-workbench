package com.xianggon.trafficops.service;

import com.xianggon.trafficops.domain.OperationLog;
import com.xianggon.trafficops.repository.OperationLogRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationLogService {

    private final OperationLogRepository operationLogRepository;

    public OperationLogService(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Transactional
    public OperationLog save(String operator, String targetType, Long targetId, String action, String detail) {
        return operationLogRepository.save(new OperationLog(operator, targetType, targetId, action, detail));
    }

    @Transactional(readOnly = true)
    public List<OperationLog> recentLogs() {
        return operationLogRepository.findTop20ByOrderByCreatedAtDesc();
    }
}
