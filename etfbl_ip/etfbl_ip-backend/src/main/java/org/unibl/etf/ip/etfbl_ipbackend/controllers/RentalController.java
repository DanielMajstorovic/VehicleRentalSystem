package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.RentalDetailsDto;
import org.unibl.etf.ip.etfbl_ipbackend.services.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired private RentalService service;

    @GetMapping
    public ResponseEntity<Page<RentalDetailsDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String searchTerm
    ) {
        Pageable p = PageRequest.of(page, size);
        return ResponseEntity.ok(service.list(p, searchTerm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDetailsDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
