package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Car}
 */
public class CarDto implements Serializable {
    private Integer id;
    private Integer vehicleId;
    private String vehicleUid;
    private BigDecimal vehiclePurchasePrice;
    private String vehicleModel;
    private Integer vehicleManufacturerId;
    private String vehicleManufacturerName;
    private String vehicleManufacturerCountry;
    private VehicleStatus vehicleStatus;
    private BigDecimal vehiclePricePerSecond;
    private BigDecimal vehicleX;
    private BigDecimal vehicleY;
    private LocalDate purchaseDate;
    private String description;

    public CarDto() {
    }

    public CarDto(Integer id, Integer vehicleId, String vehicleUid, BigDecimal vehiclePurchasePrice, String vehicleModel, Integer vehicleManufacturerId, String vehicleManufacturerName, String vehicleManufacturerCountry, VehicleStatus vehicleStatus, BigDecimal vehiclePricePerSecond, BigDecimal vehicleX, BigDecimal vehicleY, LocalDate purchaseDate, String description) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.vehicleUid = vehicleUid;
        this.vehiclePurchasePrice = vehiclePurchasePrice;
        this.vehicleModel = vehicleModel;
        this.vehicleManufacturerId = vehicleManufacturerId;
        this.vehicleManufacturerName = vehicleManufacturerName;
        this.vehicleManufacturerCountry = vehicleManufacturerCountry;
        this.vehicleStatus = vehicleStatus;
        this.vehiclePricePerSecond = vehiclePricePerSecond;
        this.vehicleX = vehicleX;
        this.vehicleY = vehicleY;
        this.purchaseDate = purchaseDate;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleUid() {
        return vehicleUid;
    }

    public void setVehicleUid(String vehicleUid) {
        this.vehicleUid = vehicleUid;
    }

    public BigDecimal getVehiclePurchasePrice() {
        return vehiclePurchasePrice;
    }

    public void setVehiclePurchasePrice(BigDecimal vehiclePurchasePrice) {
        this.vehiclePurchasePrice = vehiclePurchasePrice;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Integer getVehicleManufacturerId() {
        return vehicleManufacturerId;
    }

    public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
        this.vehicleManufacturerId = vehicleManufacturerId;
    }

    public String getVehicleManufacturerName() {
        return vehicleManufacturerName;
    }

    public void setVehicleManufacturerName(String vehicleManufacturerName) {
        this.vehicleManufacturerName = vehicleManufacturerName;
    }

    public String getVehicleManufacturerCountry() {
        return vehicleManufacturerCountry;
    }

    public void setVehicleManufacturerCountry(String vehicleManufacturerCountry) {
        this.vehicleManufacturerCountry = vehicleManufacturerCountry;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public BigDecimal getVehiclePricePerSecond() {
        return vehiclePricePerSecond;
    }

    public void setVehiclePricePerSecond(BigDecimal vehiclePricePerSecond) {
        this.vehiclePricePerSecond = vehiclePricePerSecond;
    }

    public BigDecimal getVehicleX() {
        return vehicleX;
    }

    public void setVehicleX(BigDecimal vehicleX) {
        this.vehicleX = vehicleX;
    }

    public BigDecimal getVehicleY() {
        return vehicleY;
    }

    public void setVehicleY(BigDecimal vehicleY) {
        this.vehicleY = vehicleY;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto entity = (CarDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.vehicleId, entity.vehicleId) &&
                Objects.equals(this.vehicleUid, entity.vehicleUid) &&
                Objects.equals(this.vehiclePurchasePrice, entity.vehiclePurchasePrice) &&
                Objects.equals(this.vehicleModel, entity.vehicleModel) &&
                Objects.equals(this.vehicleManufacturerId, entity.vehicleManufacturerId) &&
                Objects.equals(this.vehicleManufacturerName, entity.vehicleManufacturerName) &&
                Objects.equals(this.vehicleManufacturerCountry, entity.vehicleManufacturerCountry) &&
                Objects.equals(this.vehicleStatus, entity.vehicleStatus) &&
                Objects.equals(this.vehiclePricePerSecond, entity.vehiclePricePerSecond) &&
                Objects.equals(this.vehicleX, entity.vehicleX) &&
                Objects.equals(this.vehicleY, entity.vehicleY) &&
                Objects.equals(this.purchaseDate, entity.purchaseDate) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleId, vehicleUid, vehiclePurchasePrice, vehicleModel, vehicleManufacturerId, vehicleManufacturerName, vehicleManufacturerCountry, vehicleStatus, vehiclePricePerSecond, vehicleX, vehicleY, purchaseDate, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "vehicleId = " + vehicleId + ", " +
                "vehicleUid = " + vehicleUid + ", " +
                "vehiclePurchasePrice = " + vehiclePurchasePrice + ", " +
                "vehicleModel = " + vehicleModel + ", " +
                "vehicleManufacturerId = " + vehicleManufacturerId + ", " +
                "vehicleManufacturerName = " + vehicleManufacturerName + ", " +
                "vehicleManufacturerCountry = " + vehicleManufacturerCountry + ", " +
                "vehicleStatus = " + vehicleStatus + ", " +
                "vehiclePricePerSecond = " + vehiclePricePerSecond + ", " +
                "vehicleX = " + vehicleX + ", " +
                "vehicleY = " + vehicleY + ", " +
                "purchaseDate = " + purchaseDate + ", " +
                "description = " + description + ")";
    }
}