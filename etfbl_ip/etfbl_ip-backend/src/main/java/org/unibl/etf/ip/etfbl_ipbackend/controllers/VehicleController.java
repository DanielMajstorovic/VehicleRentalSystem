package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.*;
import org.unibl.etf.ip.etfbl_ipbackend.services.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CarService carService;
    @Autowired
    private EBikeService bikeService;
    @Autowired
    private EScooterService scooterService;
    @Autowired
    private CSVUploadService csvUploadService;
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    BreakdownRepository breakdownRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private EBikeRepository eBikeRepository;
    @Autowired
    private EScooterRepository eScooterRepository;

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars(@RequestParam(required = false) String searchTerm,
                                        Pageable pageable) {
        return vehicleService.getAllCarsNotDeleted(searchTerm, pageable);
    }

    @GetMapping("/eBikes")
    public ResponseEntity<?> getAllEbikes(@RequestParam(required = false) String searchTerm,
                                          Pageable pageable) {
        return vehicleService.getAllEBikesNotDeleted(searchTerm, pageable);
    }

    @GetMapping("/eScooters")
    public ResponseEntity<?> getAllEScooters(@RequestParam(required = false) String searchTerm,
                                             Pageable pageable) {
        return vehicleService.getAllScootersNotDeleted(searchTerm, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Integer id) {
        return vehicleService.deleteVehicle(id);
    }

    @PostMapping(value = "/createCar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCar(@RequestPart("carData") CreateCarDto createCarDTO,
                                       @RequestPart("image") MultipartFile image) {
        return carService.createCar(createCarDTO, image);
    }

    @PostMapping(value = "/createEBike", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEBike(@RequestPart("eBikeData") CreateEBikeDto createEBikeDTO,
                                         @RequestPart("image") MultipartFile image) {
        return bikeService.createEBike(createEBikeDTO, image);
    }

    @PostMapping(value = "/createEScooter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEScooter(@RequestPart("eScooterData") CreateEScooterDto createEScooterDTO,
                                            @RequestPart("image") MultipartFile image) {
        return scooterService.createEScooter(createEScooterDTO, image);
    }

    @PostMapping(value = "/uploadCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCsv(@RequestPart("file") MultipartFile file) {
        return csvUploadService.uploadCsv(file);
    }





    @GetMapping("/{vehicleId}/breakdowns")
    public ResponseEntity<?> getBreakdownsForVehicle(@PathVariable Integer vehicleId,
                                                     @RequestParam(required = false) String searchTerm,
                                                     Pageable pageable) {
        Page<Breakdown> breakdownPage;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            breakdownPage = breakdownRepository.findByVehicleVehicleid_IdAndDeleted(vehicleId, (byte) 0, pageable);
        } else {
            breakdownPage = breakdownRepository.searchBreakdownsByVehicleId(vehicleId, (byte) 0, searchTerm, pageable);
        }
        Page<BreakdownDto> dtoPage = breakdownPage.map(entity -> modelMapper.map(entity, BreakdownDto.class));
        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping("/breakdowns")
    public ResponseEntity<?> addBreakdown(@RequestBody CreateBreakdownDto createBreakdownDto) {

        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(createBreakdownDto.getVehicleId());
        if (!vehicleOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Vehicle with id=" + createBreakdownDto.getVehicleId() + " doesn't exist!");
        }

        Vehicle vehicle = vehicleOpt.get();

        vehicle.setStatus(VehicleStatus.BROKEN);
        vehicleRepository.save(vehicle);

        Breakdown breakdown = new Breakdown();
        breakdown.setDescription(createBreakdownDto.getDescription());
        breakdown.setBreakdownTime(Instant.now());
        breakdown.setDeleted((byte) 0);
        breakdown.setVehicleVehicleid(vehicle);

        breakdownRepository.save(breakdown);
        return ResponseEntity.ok("Successfully added breakdown and set vehicle status to BROKEN.");
    }


    @DeleteMapping("/breakdowns/{breakdownId}")
    @Transactional
    public ResponseEntity<?> deleteBreakdown(@PathVariable Integer breakdownId) {
        Optional<Breakdown> breakdownOpt = breakdownRepository.findById(breakdownId);
        if (!breakdownOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Breakdown with id=" + breakdownId + " doesn't exist!");
        }
        Breakdown breakdown = breakdownOpt.get();
        if (breakdown.getDeleted() != 0) {
            return ResponseEntity.badRequest().body("Breakdown with id=" + breakdownId + " is already deleted!");
        }

        breakdown.setDeleted((byte) 1);
        breakdownRepository.save(breakdown);

        Vehicle vehicle = breakdown.getVehicleVehicleid();
        long activeCount = breakdownRepository.countActiveBreakdowns(vehicle.getId());
        if (activeCount == 0) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            vehicleRepository.save(vehicle);
        }
        return ResponseEntity.ok("Successfully deleted breakdown. Vehicle status updated if needed.");
    }

    @PostMapping("/breakdowns/{breakdownId}/repair")
    @Transactional
    public ResponseEntity<?> repairBreakdown(@PathVariable Integer breakdownId) {
        Optional<Breakdown> breakdownOpt = breakdownRepository.findById(breakdownId);
        if (!breakdownOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Breakdown with id=" + breakdownId + " not found!");
        }
        Breakdown breakdown = breakdownOpt.get();
        breakdown.setRepairTime(Instant.now());
        breakdownRepository.save(breakdown);

        Vehicle vehicle = breakdown.getVehicleVehicleid();
        long activeCount = breakdownRepository.countActiveBreakdowns(vehicle.getId());
        if (activeCount == 0) {
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            vehicleRepository.save(vehicle);
        }
        return ResponseEntity.ok("Repaired successfully! Vehicle status updated if needed.");
    }


    @GetMapping("/{vehicleId}/rentals")
    public ResponseEntity<?> getRentalsForVehicle(@PathVariable Integer vehicleId,
                                                  @RequestParam(required = false) String searchTerm,
                                                  Pageable pageable) {
        Page<Rental> rentalPage;
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            rentalPage = rentalRepository.findByVehicleVehicleid_Id(vehicleId, pageable);
        } else {
            rentalPage = rentalRepository.searchRentalsByVehicleId(vehicleId, searchTerm, pageable);
        }
        Page<RentalDto> dtoPage = rentalPage.map(entity -> modelMapper.map(entity, RentalDto.class));
        return ResponseEntity.ok(dtoPage);
    }


    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarById(@PathVariable("id") Integer id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (!carOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Car with id=" + id + " doesn't exist!");
        }
        CarDto carDto = modelMapper.map(carOptional.get(), CarDto.class);
        return ResponseEntity.ok(carDto);
    }

    @GetMapping("/eBikes/{id}")
    public ResponseEntity<?> getEBikeById(@PathVariable("id") Integer id) {
        Optional<EBike> ebikeOptional = eBikeRepository.findById(id);
        if (!ebikeOptional.isPresent()) {
            return ResponseEntity.badRequest().body("E-Bike with id=" + id + " doesn't exist!");
        }
        EBikeDto ebikeDto = modelMapper.map(ebikeOptional.get(), EBikeDto.class);
        return ResponseEntity.ok(ebikeDto);
    }

    @GetMapping("/eScooters/{id}")
    public ResponseEntity<?> getEScooterById(@PathVariable("id") Integer id) {
        Optional<EScooter> escooterOptional = eScooterRepository.findById(id);
        if (!escooterOptional.isPresent()) {
            return ResponseEntity.badRequest().body("E-Scooter with id=" + id + " doesn't exist!");
        }
        EScooterDto eScooterDto = modelMapper.map(escooterOptional.get(), EScooterDto.class);
        return ResponseEntity.ok(eScooterDto);
    }


    @GetMapping("/map")
    public ResponseEntity<?> map(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) VehicleStatus status
    ) {
        return ResponseEntity.ok(vehicleService.listForMap(searchTerm, status));
    }


    @GetMapping("/for-breakdowns")
    public ResponseEntity<?> listForBreakdowns(@RequestParam(defaultValue = "")  String search,
                                               @RequestParam(required = false)  String type,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(vehicleService.searchVehicles(search.trim(), type, page, size));
    }


    @PutMapping("/{id}/price")
    public ResponseEntity<?> updatePrice(@PathVariable Integer id,
                                         @RequestBody UpdatePriceDto dto) {
        System.out.println(id + " " + dto.getPricePerSecond());
        vehicleService.updatePrice(id, dto);
        return ResponseEntity.ok("Price updated");
    }


}
