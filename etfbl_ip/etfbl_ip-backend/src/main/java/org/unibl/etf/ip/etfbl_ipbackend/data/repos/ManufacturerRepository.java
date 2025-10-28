package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.ShortManufacturerDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Manufacturer;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    @Query("SELECT new org.unibl.etf.ip.etfbl_ipbackend.data.dtos.ShortManufacturerDto(m.id, m.name, m.country) " +
            "FROM Manufacturer m WHERE m.deleted = 0")
    List<ShortManufacturerDto> findAllNotDeleted();

    Page<Manufacturer> findByDeleted(byte deleted, Pageable pageable);

    @Query("""
        SELECT m FROM Manufacturer m
        WHERE m.deleted = :deleted
          AND (
                 LOWER(m.name)    LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(m.email)   LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(m.country) LIKE LOWER(CONCAT('%', :term, '%'))
          )
        """)
    Page<Manufacturer> searchManufacturers(@Param("deleted") byte deleted,
                                           @Param("term") String term,
                                           Pageable pageable);
}