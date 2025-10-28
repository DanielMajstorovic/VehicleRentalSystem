package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.VehicleListDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.VehicleMapDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Vehicle;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByUid(String uid);

    @Query("""
SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.VehicleMapDto(
    v.y, v.x, v.pricePerSecond, v.status,
    CASE
        WHEN c.id IS NOT NULL THEN 'CAR'
        WHEN b.id IS NOT NULL THEN 'EBIKE'
        WHEN s.id IS NOT NULL THEN 'ESCOOTER'
        ELSE 'UNKNOWN'
    END,
    m.name, v.model, v.uid, v.id
)
FROM Vehicle v
JOIN v.manufacturerManufacturerid m
LEFT JOIN Car      c ON c.vehicle      = v
LEFT JOIN EBike    b ON b.vehicle      = v
LEFT JOIN EScooter s ON s.vehicle      = v
WHERE ( :searchTerm IS NULL OR (
          LOWER(v.uid)   LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
          LOWER(v.model) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
          LOWER(m.name)  LIKE LOWER(CONCAT('%', :searchTerm, '%'))
       ))
  AND ( :status IS NULL OR v.status = :status ) AND v.deleted = 0
""")
    List<VehicleMapDto> findForMap(@Param("searchTerm") String searchTerm,
                                   @Param("status")      VehicleStatus status);


    @Query("""
    SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.VehicleListDto(
         v.id, v.uid, v.model,
         CASE
              WHEN c.id IS NOT NULL THEN 'CAR'
              WHEN b.id IS NOT NULL THEN 'EBIKE'
              WHEN s.id IS NOT NULL THEN 'ESCOOTER'
              ELSE 'UNKNOWN'
         END,
         m.name,
         v.status,
         v.pricePerSecond
    )
    FROM Vehicle v
    JOIN v.manufacturerManufacturerid m
    LEFT JOIN Car      c ON c.vehicle      = v
    LEFT JOIN EBike    b ON b.vehicle      = v
    LEFT JOIN EScooter s ON s.vehicle      = v
    WHERE v.deleted = 0
      AND ( :searchTerm IS NULL OR (
               LOWER(v.uid)   LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
               LOWER(v.model) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
               LOWER(m.name)  LIKE LOWER(CONCAT('%', :searchTerm, '%'))
          ))
      AND ( :vehicleType IS NULL OR (
              (:vehicleType = 'CAR'      AND c.id IS NOT NULL) OR
              (:vehicleType = 'EBIKE'    AND b.id IS NOT NULL) OR
              (:vehicleType = 'ESCOOTER' AND s.id IS NOT NULL)
          ))
    """)
    Page<VehicleListDto> findForBreakdownEntry(@Param("searchTerm")  String searchTerm,
                                               @Param("vehicleType") String vehicleType,
                                               Pageable pageable);
}
