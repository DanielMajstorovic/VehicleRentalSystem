package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Employee;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.User;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUser(User user);

    @Query("""
        SELECT e FROM Employee e
        WHERE e.user.deleted = 0
    """)
    Page<Employee> findActive(Pageable pageable);

    @Query("""
        SELECT e FROM Employee e
        WHERE e.user.deleted = 0
          AND (
                 LOWER(e.user.username)  LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(e.user.firstName) LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(e.user.lastName)  LIKE LOWER(CONCAT('%', :term, '%'))
              OR LOWER(e.role)           LIKE LOWER(CONCAT('%', :term, '%'))
          )
    """)
    Page<Employee> searchActive(@Param("term") String term, Pageable pageable);
}