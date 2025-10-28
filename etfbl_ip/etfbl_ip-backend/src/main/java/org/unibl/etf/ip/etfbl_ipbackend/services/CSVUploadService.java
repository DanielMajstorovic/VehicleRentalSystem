package org.unibl.etf.ip.etfbl_ipbackend.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Car;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EBike;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.EScooter;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Manufacturer;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Vehicle;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.CarRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EBikeRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EScooterRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.ManufacturerRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.VehicleRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@Service
public class CSVUploadService {

    private static final String UPLOAD_DIRECTORY = "./uploads/vehicles";

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private EBikeRepository eBikeRepository;

    @Autowired
    private EScooterRepository eScooterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<?> uploadCsv(MultipartFile file) {
        int totalCount = 0;
        int successCount = 0;
        StringBuilder errorMessages = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String header = br.readLine();
            if (header == null) {
                return ResponseEntity.badRequest().body("CSV file is empty");
            }
            // Header:
            // vehicleType,vehicleUid,vehiclePurchasePrice,vehicleModel,vehicleManufacturerId,vehiclePricePerSecond,vehicleX,vehicleY,imageBase64,purchaseDate,description,autonomy,maxSpeed
            String line;
            while ((line = br.readLine()) != null) {
                totalCount++;
                String[] tokens = line.split(",");
                if (tokens.length < 9) {
                    errorMessages.append("Line ").append(totalCount)
                            .append(" skipped: not enough columns.\n");
                    continue;
                }

                String vehicleType = tokens[0].trim();
                String vehicleUid = tokens[1].trim();
                String vehiclePurchasePriceStr = tokens[2].trim();
                String vehicleModel = tokens[3].trim();
                String manufacturerIdStr = tokens[4].trim();
                String vehiclePricePerSecondStr = tokens[5].trim();
                String vehicleXStr = tokens[6].trim();
                String vehicleYStr = tokens[7].trim();
                String imageBase64 = tokens[8].trim();

                String purchaseDate = tokens.length > 9 ? tokens[9].trim() : "";
                String description = tokens.length > 10 ? tokens[10].trim() : "";
                String autonomyStr = tokens.length > 11 ? tokens[11].trim() : "";
                String maxSpeedStr = tokens.length > 12 ? tokens[12].trim() : "";

                if (vehicleUid.isEmpty() || vehiclePurchasePriceStr.isEmpty() ||
                        vehicleModel.isEmpty() || manufacturerIdStr.isEmpty()) {
                    errorMessages.append("Line ").append(totalCount)
                            .append(" skipped: a required field is missing.\n");
                    continue;
                }

                Optional<Vehicle> existingVehicle = vehicleRepository.findByUid(vehicleUid);
                if(existingVehicle.isPresent()){
                    errorMessages.append("Line ").append(totalCount)
                            .append(" skipped: Vehicle with UID '").append(vehicleUid)
                            .append("' already exists.\n");
                    continue;
                }

                try {
                    BigDecimal vehiclePurchasePrice = new BigDecimal(vehiclePurchasePriceStr);
                    BigDecimal vehiclePricePerSecond = new BigDecimal(vehiclePricePerSecondStr);
                    BigDecimal vehicleX = new BigDecimal(vehicleXStr);
                    BigDecimal vehicleY = new BigDecimal(vehicleYStr);
                    Integer manufacturerId = Integer.parseInt(manufacturerIdStr);

                    Optional<Manufacturer> manufacturerOptional = manufacturerRepository.findById(manufacturerId);
                    if (!manufacturerOptional.isPresent()) {
                        errorMessages.append("Line ").append(totalCount)
                                .append(" skipped: Manufacturer not found.\n");
                        continue;
                    }
                    Manufacturer manufacturer = manufacturerOptional.get();

                    Vehicle vehicle = new Vehicle();
                    vehicle.setUid(vehicleUid);
                    vehicle.setPurchasePrice(vehiclePurchasePrice);
                    vehicle.setModel(vehicleModel);
                    vehicle.setManufacturerManufacturerid(manufacturer);
                    vehicle.setStatus(VehicleStatus.AVAILABLE);
                    vehicle.setDeleted((byte) 0);
                    vehicle.setPricePerSecond(vehiclePricePerSecond);
                    vehicle.setX(vehicleX);
                    vehicle.setY(vehicleY);

                    Vehicle savedVehicle = vehicleRepository.save(vehicle);

                    // Image: base64 save as .jpg file
                    if (!imageBase64.isEmpty()) {
                        try {
                            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
                            String fileName = vehicleUid + ".jpg";
                            Path path = Paths.get(UPLOAD_DIRECTORY, fileName);
                            Files.write(path, imageBytes);
                        } catch (Exception ex) {
                            errorMessages.append("Line ").append(totalCount)
                                    .append(" warning: Failed to save image.\n");
                        }
                    }

                    switch (vehicleType.toLowerCase()) {
                        case "car":
                            Car car = new Car();
                            car.setVehicle(savedVehicle);
                            car.setPurchaseDate(LocalDate.parse(purchaseDate));
                            car.setDescription(description);
                            carRepository.save(car);
                            successCount++;
                            break;
                        case "ebike":
                            EBike eBike = new EBike();
                            eBike.setVehicle(savedVehicle);
                            Integer autonomy = autonomyStr.isEmpty() ? 50 : Integer.parseInt(autonomyStr);
                            eBike.setAutonomy(autonomy);
                            eBikeRepository.save(eBike);
                            successCount++;
                            break;
                        case "escooter":
                            EScooter eScooter = new EScooter();
                            eScooter.setVehicle(savedVehicle);
                            Integer maxSpeed = maxSpeedStr.isEmpty() ? 25 : Integer.parseInt(maxSpeedStr);
                            eScooter.setMaxSpeed(maxSpeed);
                            eScooterRepository.save(eScooter);
                            successCount++;
                            break;
                        default:
                            errorMessages.append("Line ").append(totalCount)
                                    .append(" skipped: Unknown vehicle type ('")
                                    .append(vehicleType).append("').\n");
                            vehicleRepository.delete(savedVehicle);
                    }
                } catch (Exception e) {
                    errorMessages.append("Line ").append(totalCount)
                            .append(" skipped: ").append(e.getMessage()).append(".\n");
                }
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error processing CSV file: " + ex.getMessage());
        }

        String message = "Successfully added " + successCount + " vehicles (" + totalCount + " records processed).\n";
        if (errorMessages.length() > 0) {
            message += "Details:\n" + errorMessages.toString();
        }
        return ResponseEntity.ok(message);
    }
}
