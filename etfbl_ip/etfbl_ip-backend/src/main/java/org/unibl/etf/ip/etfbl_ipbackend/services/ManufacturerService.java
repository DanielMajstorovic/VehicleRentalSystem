package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Manufacturer;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.ManufacturerRepository;

import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> getAllNotDeleted(String searchTerm, Pageable pageable) {
        Page<Manufacturer> page = (searchTerm == null || searchTerm.trim().isEmpty())
                ? repository.findByDeleted((byte) 0, pageable)
                : repository.searchManufacturers((byte) 0, searchTerm.trim(), pageable);

        Page<ManufacturerDto> dtoPage = page.map(m -> modelMapper.map(m, ManufacturerDto.class));
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<?> createManufacturer(CreateManufacturerDto dto) {
        Manufacturer m = modelMapper.map(dto, Manufacturer.class);
        m.setDeleted((byte) 0);
        repository.save(m);
        return ResponseEntity.ok("Manufacturer created successfully.");
    }

    @Transactional
    public ResponseEntity<?> updateManufacturer(Integer id, UpdateManufacturerDto dto) {
        Optional<Manufacturer> opt = repository.findById(id);
        if (opt.isEmpty() || opt.get().getDeleted() != 0)
            return ResponseEntity.badRequest().body("Manufacturer with id=" + id + " doesn't exist!");

        Manufacturer m = opt.get();
        modelMapper.map(dto, m);
        repository.save(m);
        return ResponseEntity.ok("Manufacturer updated successfully.");
    }

    @Transactional
    public ResponseEntity<?> deleteManufacturer(Integer id) {
        Optional<Manufacturer> opt = repository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.badRequest().body("Manufacturer with id=" + id + " doesn't exist!");

        Manufacturer m = opt.get();
        if (m.getDeleted() != 0)
            return ResponseEntity.badRequest().body("Manufacturer with id=" + id + " is already deleted!");

        m.setDeleted((byte) 1);
        repository.save(m);
        return ResponseEntity.ok("Manufacturer deleted successfully.");
    }

    public ResponseEntity<?> getById(Integer id) {
        Optional<Manufacturer> opt = repository.findById(id);
        if (opt.isEmpty() || opt.get().getDeleted() != 0)
            return ResponseEntity.badRequest().body("Manufacturer with id=" + id + " doesn't exist!");

        ManufacturerDto dto = modelMapper.map(opt.get(), ManufacturerDto.class);
        return ResponseEntity.ok(dto);
    }
}
