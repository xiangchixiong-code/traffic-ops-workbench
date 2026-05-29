package com.xianggon.trafficops.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_logs")
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String operator;

    @Column(nullable = false, length = 40)
    private String targetType;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false, length = 80)
    private String action;

    @Column(nullable = false, length = 300)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected OperationLog() {
    }

    public OperationLog(String operator, String targetType, Long targetId, String action, String detail) {
        this.operator = operator;
        this.targetType = targetType;
        this.targetId = targetId;
        this.action = action;
        this.detail = detail;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getOperator() {
        return operator;
    }

    public String getTargetType() {
        return targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public String getAction() {
        return action;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
