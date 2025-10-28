package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndDeleted(String username, byte deleted);

}