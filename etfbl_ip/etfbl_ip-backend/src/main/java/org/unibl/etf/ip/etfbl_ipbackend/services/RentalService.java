package org.unibl.etf.ip.etfbl_ipbackend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.RentalDetailsDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Rental;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Page<RentalDetailsDto> list(Pageable pageable, String searchTerm) {
        if (searchTerm != null && !searchTerm.isBlank()) {
            return rentalRepository.search(pageable, searchTerm)
                    .map(r -> modelMapper.map(r, RentalDetailsDto.class));
        }
        return rentalRepository.findAll(pageable)
                .map(r -> modelMapper.map(r, RentalDetailsDto.class));
    }

    public RentalDetailsDto getById(Integer id) {
        Rental r = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found: " + id));
        return modelMapper.map(r, RentalDetailsDto.class);
    }
}
