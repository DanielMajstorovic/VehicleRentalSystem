package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Promotion;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.PromotionRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/promotions")
public class PromotionController {
    @Autowired private PromotionRepository repo;

    @PostMapping
    public ResponseEntity<Promotion> create(@RequestBody Promotion promo) {
        return ResponseEntity.ok(repo.save(promo));
    }
}
