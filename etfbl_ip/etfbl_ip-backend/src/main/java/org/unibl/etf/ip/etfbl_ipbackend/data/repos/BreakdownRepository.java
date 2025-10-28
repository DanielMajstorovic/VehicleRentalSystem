package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.BreakdownsCountDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Breakdown;

import java.util.List;

public interface BreakdownRepository extends JpaRepository<Breakdown, Integer> {
    Page<Breakdown> findByVehicleVehicleid_IdAndDeleted(Integer vehicleId, Byte deleted, Pageable pageable);

    @Query("SELECT b FROM Breakdown b WHERE b.vehicleVehicleid.id = :vehicleId AND b.deleted = :deleted AND LOWER(b.description) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<Breakdown> searchBreakdownsByVehicleId(@Param("vehicleId") Integer vehicleId,
                                                @Param("deleted") Byte deleted,
                                                @Param("term") String term,
                                                Pageable pageable);

    @Modifying
    @Query("UPDATE Breakdown b SET b.deleted = 1 WHERE b.id = :id")
    int markBreakdownAsDeleted(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Breakdown b SET b.repairTime = CURRENT_TIMESTAMP WHERE b.id = :id")
    int repairBreakdown(@Param("id") Integer id);

    @Query("SELECT COUNT(b) FROM Breakdown b WHERE b.vehicleVehicleid.id = :vehicleId AND b.deleted = 0 AND b.repairTime IS NULL")
    long countActiveBreakdowns(@Param("vehicleId") Integer vehicleId);

    @Query("""
    SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.BreakdownsCountDto(
            v.uid, COUNT(b)
    )
    FROM Breakdown b
    JOIN b.vehicleVehicleid v
    WHERE b.deleted = 0
      AND b.repairTime IS NULL
    GROUP BY v.uid
    ORDER BY COUNT(b) DESC
    """)
    List<BreakdownsCountDto> unresolvedCounts();
}