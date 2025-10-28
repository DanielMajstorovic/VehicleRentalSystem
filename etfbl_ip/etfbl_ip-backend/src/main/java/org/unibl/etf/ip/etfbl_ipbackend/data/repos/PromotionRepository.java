package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}