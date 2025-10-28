package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.services.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired private ClientService service;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String searchTerm,
                                  @RequestParam(required = false) Byte deleted,
                                  Pageable pageable) {
        return service.list(searchTerm, deleted, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<?> block(@PathVariable Integer id) {
        return service.setBlocked(id, true);
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<?> unblock(@PathVariable Integer id) {
        return service.setBlocked(id, false);
    }
}
