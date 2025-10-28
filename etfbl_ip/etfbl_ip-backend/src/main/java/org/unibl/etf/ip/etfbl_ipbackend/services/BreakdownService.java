package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.CreateBreakdownDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Breakdown;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Vehicle;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.BreakdownRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.VehicleRepository;

import java.time.Instant;

@Service
public class BreakdownService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private BreakdownRepository breakdownRepository;

    @Transactional
    public void createBreakdown(CreateBreakdownDto dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Breakdown b = new Breakdown();
        b.setDescription(dto.getDescription());
        b.setBreakdownTime(Instant.now());
        b.setVehicleVehicleid(vehicle);
        b.setDeleted((byte) 0);
        breakdownRepository.save(b);

        if (vehicle.getStatus() != VehicleStatus.BROKEN) {
            vehicle.setStatus(VehicleStatus.BROKEN);
        }
    }


}
