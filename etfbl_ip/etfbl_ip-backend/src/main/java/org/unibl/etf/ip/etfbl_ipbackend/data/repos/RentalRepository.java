package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.IncomeByTypeDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.IncomePerDayDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Page<Rental> findByVehicleVehicleid_Id(Integer vehicleId, Pageable pageable);

    @Query("SELECT r FROM Rental r WHERE r.vehicleVehicleid.id = :vehicleId AND " +
            "(LOWER(r.driversLicense) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(r.paymentCard) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<Rental> searchRentalsByVehicleId(@Param("vehicleId") Integer vehicleId,
                                          @Param("term") String term,
                                          Pageable pageable);


    @Query("""
        SELECT r FROM Rental r
        JOIN r.clientUserUserid c
        JOIN c.user u
        JOIN r.vehicleVehicleid v
        WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(v.uid) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(v.model) LIKE LOWER(CONCAT('%', :term, '%'))
        """)
    Page<Rental> search(Pageable pageable, String term);


    @Query("""
    SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.IncomePerDayDto(
        day(r.endTime),
        sum(r.totalAmount)
    )
    FROM Rental r
    WHERE r.endTime IS NOT NULL
      AND r.totalAmount IS NOT NULL
      AND year(r.endTime)  = :year
      AND month(r.endTime) = :month
    GROUP BY day(r.endTime)
    ORDER BY day(r.endTime)
    """)
    List<IncomePerDayDto> incomePerDay(
            @Param("year")  int year,
            @Param("month") int month
    );


    @Query("""
    SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.IncomeByTypeDto(
         CASE
            WHEN c.id IS NOT NULL THEN 'CAR'
            WHEN b.id IS NOT NULL THEN 'EBIKE'
            WHEN s.id IS NOT NULL THEN 'ESCOOTER'
            ELSE 'UNKNOWN'
         END,
         SUM(r.totalAmount)
    )
    FROM Rental r
    JOIN r.vehicleVehicleid v
    LEFT JOIN Car      c ON c.vehicle = v
    LEFT JOIN EBike    b ON b.vehicle = v
    LEFT JOIN EScooter s ON s.vehicle = v
    WHERE r.endTime IS NOT NULL
      AND r.totalAmount IS NOT NULL
      AND FUNCTION('year',  r.endTime) = :year
      AND FUNCTION('month', r.endTime) = :month
    GROUP BY
         CASE
            WHEN c.id IS NOT NULL THEN 'CAR'
            WHEN b.id IS NOT NULL THEN 'EBIKE'
            WHEN s.id IS NOT NULL THEN 'ESCOOTER'
            ELSE 'UNKNOWN'
         END
    """)
    List<IncomeByTypeDto> incomeByType(int year, int month);
}