package com.xianggon.trafficops.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "road_sections")
public class RoadSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 40)
    private String code;

    @Column(nullable = false, length = 80)
    private String region;

    @Column(nullable = false)
    private Double mileage;

    protected RoadSection() {
    }

    public RoadSection(String name, String code, String region, Double mileage) {
        this.name = name;
        this.code = code;
        this.region = region;
        this.mileage = mileage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getRegion() {
        return region;
    }

    public Double getMileage() {
        return mileage;
    }
}
