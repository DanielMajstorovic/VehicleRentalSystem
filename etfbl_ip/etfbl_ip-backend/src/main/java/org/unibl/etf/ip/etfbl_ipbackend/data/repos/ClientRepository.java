package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Client;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.User;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByUser(User user);

    @Query("""
        SELECT c FROM Client c
        WHERE (:deleted IS NULL OR c.user.deleted = :deleted)
    """)
    Page<Client> findAllFiltered(@Param("deleted") Byte deleted, Pageable pageable);

    @Query("""
        SELECT c FROM Client c
        WHERE (:deleted IS NULL OR c.user.deleted = :deleted)
          AND (
                 LOWER(c.user.username)     LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.user.firstName)    LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.user.lastName)     LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.email)            LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.phone)            LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.iDNumber)         LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(c.passportNumber)   LIKE LOWER(CONCAT('%', :term, '%'))
          )
    """)
    Page<Client> searchFiltered(@Param("term") String term,
                                @Param("deleted") Byte deleted,
                                Pageable pageable);
}