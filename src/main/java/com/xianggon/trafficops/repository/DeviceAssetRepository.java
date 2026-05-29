package com.xianggon.trafficops.repository;

import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceAssetRepository extends JpaRepository<DeviceAsset, Long> {

    long countByStatus(DeviceStatus status);

    @Query("""
            select d from DeviceAsset d
            join fetch d.roadSection r
            where (:keyword is null or lower(d.name) like lower(concat('%', :keyword, '%'))
                or lower(d.assetCode) like lower(concat('%', :keyword, '%')))
              and (:status is null or d.status = :status)
              and (:roadSectionId is null or r.id = :roadSectionId)
            order by d.id desc
            """)
    List<DeviceAsset> search(@Param("keyword") String keyword,
            @Param("status") DeviceStatus status,
            @Param("roadSectionId") Long roadSectionId);
}
