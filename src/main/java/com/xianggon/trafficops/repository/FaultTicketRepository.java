package com.xianggon.trafficops.repository;

import com.xianggon.trafficops.domain.FaultTicket;
import com.xianggon.trafficops.domain.TicketStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FaultTicketRepository extends JpaRepository<FaultTicket, Long> {

    long countByStatus(TicketStatus status);

    @Query("""
            select t from FaultTicket t
            join fetch t.deviceAsset d
            join fetch d.roadSection
            where (:status is null or t.status = :status)
            order by t.createdAt desc
            """)
    List<FaultTicket> search(@Param("status") TicketStatus status);
}
