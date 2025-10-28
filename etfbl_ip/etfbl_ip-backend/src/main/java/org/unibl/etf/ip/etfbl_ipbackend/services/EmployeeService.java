package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.*;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper mapper;

    public ResponseEntity<?> list(String term, Pageable pageable) {
        Page<Employee> p = (term == null || term.isBlank())
                ? employeeRepo.findActive(pageable)
                : employeeRepo.searchActive(term.trim(), pageable);

        Page<EmployeeDto> dtoPage = p.map(e -> {
            EmployeeDto d = mapper.map(e, EmployeeDto.class);
            d.setUserUsername(e.getUser().getUsername());
            return d;
        });
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<?> create(CreateEmployeeDto dto) {
        // duplicate check on username
        if (userRepo.existsByUsernameAndDeleted(dto.getUserUsername(), (byte)0))
            return ResponseEntity.status(500)
                    .body("User with this username already exists!");


        User u = new User();
        u.setUsername(dto.getUserUsername());
        u.setPassword(dto.getUserPassword());
        u.setFirstName(dto.getUserFirstName());
        u.setLastName(dto.getUserLastName());
        u.setDeleted((byte)0);
        userRepo.save(u);


        Employee e = new Employee();
        e.setUser(u);
        e.setRole(dto.getRole());
        employeeRepo.save(e);

        return ResponseEntity.ok("Employee created.");
    }

    @Transactional
    public ResponseEntity<?> update(Integer id, UpdateEmployeeDto dto) {
        Optional<Employee> opt = employeeRepo.findById(id);
        if (opt.isEmpty() || opt.get().getUser().getDeleted() != 0)
            return ResponseEntity.badRequest()
                    .body("Employee with id=" + id + " doesn't exist!");

        Employee e = opt.get();
        User u = e.getUser();

        u.setFirstName(dto.getUserFirstName());
        u.setLastName(dto.getUserLastName());
        if (dto.getUserPassword() != null && !dto.getUserPassword().isBlank())
            u.setPassword(dto.getUserPassword());
        e.setRole(dto.getRole());

        userRepo.save(u);
        employeeRepo.save(e);
        return ResponseEntity.ok("Employee updated.");
    }

    @Transactional
    public ResponseEntity<?> delete(Integer id) {
        Optional<Employee> opt = employeeRepo.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.badRequest().body("Employee not found!");

        User u = opt.get().getUser();
        if (u.getDeleted() != 0)
            return ResponseEntity.badRequest().body("Already deleted!");

        u.setDeleted((byte)1);
        userRepo.save(u);
        return ResponseEntity.ok("Employee deleted.");
    }
}
