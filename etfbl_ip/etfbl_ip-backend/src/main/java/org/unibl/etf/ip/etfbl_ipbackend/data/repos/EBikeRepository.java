package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EBike;

import java.util.List;

public interface EBikeRepository extends JpaRepository<EBike, Integer> {
    Page<EBike> findByVehicle_Deleted(Byte deleted, Pageable pageable);

    @Query("SELECT e FROM EBike e WHERE e.vehicle.deleted = :deleted AND " +
            "(LOWER(e.vehicle.model) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(e.vehicle.uid) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<EBike> searchByModelOrUid(@Param("deleted") Byte deleted,
                                   @Param("term") String term,
                                   Pageable pageable);
}