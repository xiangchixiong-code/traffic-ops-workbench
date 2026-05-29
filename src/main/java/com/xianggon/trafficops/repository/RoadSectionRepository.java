package com.xianggon.trafficops.repository;

import com.xianggon.trafficops.domain.RoadSection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadSectionRepository extends JpaRepository<RoadSection, Long> {

    Optional<RoadSection> findByCode(String code);
}
