package com.xianggon.trafficops.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "inspection_records")
public class InspectionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_asset_id")
    private DeviceAsset deviceAsset;

    @Column(nullable = false, length = 40)
    private String inspector;

    @Column(nullable = false)
    private LocalDateTime inspectedAt;

    @Column(nullable = false, length = 20)
    private String result;

    @Column(nullable = false, length = 300)
    private String notes;

    protected InspectionRecord() {
    }

    public InspectionRecord(DeviceAsset deviceAsset, String inspector, LocalDateTime inspectedAt,
            String result, String notes) {
        this.deviceAsset = deviceAsset;
        this.inspector = inspector;
        this.inspectedAt = inspectedAt;
        this.result = result;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public DeviceAsset getDeviceAsset() {
        return deviceAsset;
    }

    public String getInspector() {
        return inspector;
    }

    public LocalDateTime getInspectedAt() {
        return inspectedAt;
    }

    public String getResult() {
        return result;
    }

    public String getNotes() {
        return notes;
    }
}
