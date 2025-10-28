package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.*;
import org.unibl.etf.ip.etfbl_ipbackend.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired private EmployeeService service;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String searchTerm,
                                  Pageable pageable) {
        return service.list(searchTerm, pageable);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateEmployeeDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody @Valid UpdateEmployeeDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
