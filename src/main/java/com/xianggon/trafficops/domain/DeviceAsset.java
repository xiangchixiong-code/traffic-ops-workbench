package com.xianggon.trafficops.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "device_assets")
public class DeviceAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, unique = true, length = 40)
    private String assetCode;

    @Column(nullable = false, length = 40)
    private String deviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeviceStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "road_section_id")
    private RoadSection roadSection;

    @Column(nullable = false, length = 80)
    private String location;

    @Column(nullable = false)
    private LocalDate installedAt;

    protected DeviceAsset() {
    }

    public DeviceAsset(String name, String assetCode, String deviceType, DeviceStatus status,
            RoadSection roadSection, String location, LocalDate installedAt) {
        this.name = name;
        this.assetCode = assetCode;
        this.deviceType = deviceType;
        this.status = status;
        this.roadSection = roadSection;
        this.location = location;
        this.installedAt = installedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public RoadSection getRoadSection() {
        return roadSection;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getInstalledAt() {
        return installedAt;
    }

    public void updateStatus(DeviceStatus status) {
        this.status = status;
    }
}
