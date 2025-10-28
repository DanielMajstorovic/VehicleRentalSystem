package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EScooter;

import java.util.List;

public interface EScooterRepository extends JpaRepository<EScooter, Integer> {
    Page<EScooter> findByVehicle_Deleted(Byte deleted, Pageable pageable);

    @Query("SELECT s FROM EScooter s WHERE s.vehicle.deleted = :deleted AND " +
            "(LOWER(s.vehicle.model) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(s.vehicle.uid) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<EScooter> searchByModelOrUid(@Param("deleted") Byte deleted,
                                      @Param("term") String term,
                                      Pageable pageable);
}