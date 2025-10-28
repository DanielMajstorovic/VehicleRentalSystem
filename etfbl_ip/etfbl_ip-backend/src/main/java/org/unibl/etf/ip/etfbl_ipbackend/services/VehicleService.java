package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Car;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EBike;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EScooter;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Vehicle;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.CarRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EBikeRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EScooterRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.VehicleRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    EBikeRepository ebikeRepository;

    @Autowired
    private EScooterRepository escooterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VehicleRepository vehicleRepository;

    public ResponseEntity<?> getAllCarsNotDeleted(String searchTerm, Pageable pageable) {
        Page<Car> carsPage;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            carsPage = carRepository.findByVehicle_Deleted((byte) 0, pageable);
        } else {
            carsPage = carRepository.searchByModelOrUid((byte) 0, searchTerm, pageable);
        }

        Page<CarDto> dtoPage = carsPage.map(e -> modelMapper.map(e, CarDto.class));
        return ResponseEntity.ok(dtoPage);
    }

    public ResponseEntity<?> getAllEBikesNotDeleted(String searchTerm, Pageable pageable) {
        Page<EBike> eBikesPage;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            eBikesPage = ebikeRepository.findByVehicle_Deleted((byte) 0, pageable);
        } else {
            eBikesPage = ebikeRepository.searchByModelOrUid((byte) 0, searchTerm, pageable);
        }

        Page<EBikeDto> dtoPage = eBikesPage.map(e -> modelMapper.map(e, EBikeDto.class));
        return ResponseEntity.ok(dtoPage);
    }

    public ResponseEntity<?> getAllScootersNotDeleted(String searchTerm, Pageable pageable) {
        Page<EScooter> scootersPage;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            scootersPage = escooterRepository.findByVehicle_Deleted((byte)0, pageable);
        } else {
            scootersPage = escooterRepository.searchByModelOrUid((byte)0, searchTerm, pageable);
        }

        Page<EScooterDto> dtoPage = scootersPage.map(e -> modelMapper.map(e, EScooterDto.class));
        return ResponseEntity.ok(dtoPage);
    }

    public ResponseEntity<?> deleteVehicle(Integer vehicleId) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);

        if(!vehicleOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Vehicle with id=" + vehicleId + " does not exist!");
        }

        Vehicle vehicle = vehicleOptional.get();

        vehicle.setDeleted((byte)1);
        vehicleRepository.save(vehicle);
        vehicleRepository.flush();
        return ResponseEntity.ok().body("Vehicle [" + vehicle.getUid() + "] has been deleted!");
    }


    public List<VehicleMapDto> listForMap(String searchTerm, VehicleStatus status) {
        if (searchTerm != null && searchTerm.isBlank()) searchTerm = null;
        return vehicleRepository.findForMap(searchTerm, status);
    }


    public Page<VehicleListDto> searchVehicles(String searchTerm,
                                               String vehicleType,
                                               int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("uid").ascending());
        return vehicleRepository.findForBreakdownEntry(
                searchTerm == null || searchTerm.isBlank() ? null : searchTerm,
                vehicleType == null || vehicleType.isBlank() ? null : vehicleType.toUpperCase(),
                pageable);
    }


    @Transactional
    public void updatePrice(Integer vehicleId, UpdatePriceDto dto) {
        if (dto.getPricePerSecond() == null || dto.getPricePerSecond().signum() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        Vehicle v = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        v.setPricePerSecond(dto.getPricePerSecond());
    }

}
