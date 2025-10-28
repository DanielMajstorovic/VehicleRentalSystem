package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.CreateEBikeDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.CreateEScooterDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.EBikeDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.EScooterDto;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EBike;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EScooter;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Manufacturer;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Vehicle;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EBikeRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EScooterRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.ManufacturerRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.VehicleRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class EScooterService {

    private static final String UPLOAD_DIRECTORY = "./uploads/vehicles";

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EScooterRepository eScooterRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<?> createEScooter(CreateEScooterDto createEScooterDtoDto, MultipartFile image) {
        Optional<Manufacturer> manufacturerOptional = manufacturerRepository.findById(createEScooterDtoDto.getVehicleManufacturerId());
        if (!manufacturerOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Manufacturer not found");
        }
        Manufacturer manufacturer = manufacturerOptional.get();

        Vehicle vehicle = new Vehicle();
        vehicle.setUid(createEScooterDtoDto.getVehicleUid());
        vehicle.setPurchasePrice(createEScooterDtoDto.getVehiclePurchasePrice());
        vehicle.setModel(createEScooterDtoDto.getVehicleModel());
        vehicle.setManufacturerManufacturerid(manufacturer);
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicle.setDeleted((byte) 0);
        vehicle.setPricePerSecond(createEScooterDtoDto.getVehiclePricePerSecond());
        vehicle.setX(createEScooterDtoDto.getVehicleX());
        vehicle.setY(createEScooterDtoDto.getVehicleY());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        EScooter eScooter = new EScooter();
        eScooter.setVehicle(savedVehicle);
        eScooter.setMaxSpeed(createEScooterDtoDto.getMaxSpeed());

        EScooter savedEScooter = eScooterRepository.save(eScooter);

        if (image != null && !image.isEmpty()) {
            String fileName = vehicle.getUid() + ".jpg";
            Path path = Paths.get(UPLOAD_DIRECTORY, fileName);
            try {
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                System.out.println("Error writing image" + e.getMessage());
            }
        }

        return ResponseEntity.ok(modelMapper.map(savedEScooter, EScooterDto.class));

    }



}
