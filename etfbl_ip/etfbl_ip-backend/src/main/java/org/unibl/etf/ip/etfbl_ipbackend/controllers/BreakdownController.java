package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.CreateBreakdownDto;
import org.unibl.etf.ip.etfbl_ipbackend.services.BreakdownService;
import org.unibl.etf.ip.etfbl_ipbackend.services.VehicleService;

@RestController
@RequestMapping("/breakdowns")
public class BreakdownController {

    @Autowired
    private BreakdownService breakdownService;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateBreakdownDto dto) {
        breakdownService.createBreakdown(dto);
        return ResponseEntity.ok("Breakdown recorded");
    }
}
