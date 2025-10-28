package org.unibl.etf.ip.etfbl_ipbackend.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.CreateManufacturerDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.ShortManufacturerDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.UpdateManufacturerDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.ManufacturerRepository;
import org.unibl.etf.ip.etfbl_ipbackend.services.ManufacturerService;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/short")
    public List<ShortManufacturerDto> getAllNotDeletedShortManufacturers() {
        return manufacturerRepository.findAllNotDeleted();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String searchTerm,
                                    Pageable pageable) {
        return manufacturerService.getAllNotDeleted(searchTerm, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return manufacturerService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateManufacturerDto dto) {
        return manufacturerService.createManufacturer(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody @Valid UpdateManufacturerDto dto) {
        return manufacturerService.updateManufacturer(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return manufacturerService.deleteManufacturer(id);
    }
}