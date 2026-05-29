package com.xianggon.trafficops.repository;

import com.xianggon.trafficops.domain.InspectionRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, Long> {

    @Query("""
            select i from InspectionRecord i
            join fetch i.deviceAsset d
            join fetch d.roadSection
            order by i.inspectedAt desc
            """)
    List<InspectionRecord> findRecent();
}
