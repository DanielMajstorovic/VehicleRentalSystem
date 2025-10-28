package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.*;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BreakdownRepository breakdownRepository;

    @GetMapping("/income-per-day")
    public ResponseEntity<?> incomePerDay(@RequestParam int year,
                                          @RequestParam int month) {
        return ResponseEntity.ok(rentalRepository.incomePerDay(year, month));
    }

    @GetMapping("/income-by-type")
    public ResponseEntity<?> incomeByType(@RequestParam int year,
                                          @RequestParam int month) {
        return ResponseEntity.ok(rentalRepository.incomeByType(year, month));
    }

    @GetMapping("/breakdowns-count")
    public ResponseEntity<?> breakdownCounts() {
        return ResponseEntity.ok(breakdownRepository.unresolvedCounts());
    }
}
