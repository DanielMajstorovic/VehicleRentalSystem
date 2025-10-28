package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.ClientDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.*;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired private ClientRepository clientRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper mapper;

    public ResponseEntity<?> list(String term, Byte deleted, Pageable page) {
        Page<Client> p = (term == null || term.isBlank())
                ? clientRepo.findAllFiltered(deleted, page)
                : clientRepo.searchFiltered(term.trim(), deleted, page);

        Page<ClientDto> dto = p.map(c -> {
            ClientDto d = mapper.map(c, ClientDto.class);
            d.setUserUsername(c.getUser().getUsername());
            d.setUserFirstName(c.getUser().getFirstName());
            d.setUserLastName(c.getUser().getLastName());
            d.setUserDeleted(c.getUser().getDeleted());
            return d;
        });
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> get(Integer id) {
        Optional<Client> client = clientRepo.findById(id);
        if (client.isPresent()) {
            Client c = client.get();
            ClientDto d = mapper.map(c, ClientDto.class);
            d.setUserUsername(c.getUser().getUsername());
            d.setUserFirstName(c.getUser().getFirstName());
            d.setUserLastName(c.getUser().getLastName());
            d.setUserDeleted(c.getUser().getDeleted());
            return ResponseEntity.ok(d);
        }
        return ResponseEntity.badRequest()
                        .body("Client with id=" + id + " not found!");
    }

    @Transactional
    public ResponseEntity<?> setBlocked(Integer id, boolean blocked) {
        Optional<Client> opt = clientRepo.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.badRequest()
                    .body("Client with id=" + id + " not found!");

        User u = opt.get().getUser();
        u.setDeleted((byte) (blocked ? 1 : 0));
        userRepo.save(u);
        return ResponseEntity.ok(blocked ? "Client blocked." : "Client activated.");
    }
}
