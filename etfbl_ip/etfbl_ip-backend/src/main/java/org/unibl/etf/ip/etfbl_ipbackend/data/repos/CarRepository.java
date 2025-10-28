package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    Page<Car> findByVehicle_Deleted(Byte deleted, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE c.vehicle.deleted = :deleted AND " +
            "(LOWER(c.vehicle.model) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(c.vehicle.uid) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<Car> searchByModelOrUid(@Param("deleted") Byte deleted,
                                 @Param("term") String term,
                                 Pageable pageable);

}