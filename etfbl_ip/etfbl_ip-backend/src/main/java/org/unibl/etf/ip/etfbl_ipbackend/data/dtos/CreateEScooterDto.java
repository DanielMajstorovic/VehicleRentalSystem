package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.EScooter}
 */
public class CreateEScooterDto implements Serializable {
    @NotNull
    private String vehicleUid;
    @NotNull
    private BigDecimal vehiclePurchasePrice;
    @NotNull
    private String vehicleModel;
    @NotNull
    private Integer vehicleManufacturerId;
    @NotNull
    private BigDecimal vehiclePricePerSecond;
    @NotNull
    private BigDecimal vehicleX;
    @NotNull
    private BigDecimal vehicleY;
    @NotNull
    private Integer maxSpeed;

    public CreateEScooterDto() {
    }

    public CreateEScooterDto(String vehicleUid, BigDecimal vehiclePurchasePrice, String vehicleModel, Integer vehicleManufacturerId, BigDecimal vehiclePricePerSecond, BigDecimal vehicleX, BigDecimal vehicleY, Integer maxSpeed) {
        this.vehicleUid = vehicleUid;
        this.vehiclePurchasePrice = vehiclePurchasePrice;
        this.vehicleModel = vehicleModel;
        this.vehicleManufacturerId = vehicleManufacturerId;
        this.vehiclePricePerSecond = vehiclePricePerSecond;
        this.vehicleX = vehicleX;
        this.vehicleY = vehicleY;
        this.maxSpeed = maxSpeed;
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

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateEScooterDto entity = (CreateEScooterDto) o;
        return Objects.equals(this.vehicleUid, entity.vehicleUid) &&
                Objects.equals(this.vehiclePurchasePrice, entity.vehiclePurchasePrice) &&
                Objects.equals(this.vehicleModel, entity.vehicleModel) &&
                Objects.equals(this.vehicleManufacturerId, entity.vehicleManufacturerId) &&
                Objects.equals(this.vehiclePricePerSecond, entity.vehiclePricePerSecond) &&
                Objects.equals(this.vehicleX, entity.vehicleX) &&
                Objects.equals(this.vehicleY, entity.vehicleY) &&
                Objects.equals(this.maxSpeed, entity.maxSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleUid, vehiclePurchasePrice, vehicleModel, vehicleManufacturerId, vehiclePricePerSecond, vehicleX, vehicleY, maxSpeed);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "vehicleUid = " + vehicleUid + ", " +
                "vehiclePurchasePrice = " + vehiclePurchasePrice + ", " +
                "vehicleModel = " + vehicleModel + ", " +
                "vehicleManufacturerId = " + vehicleManufacturerId + ", " +
                "vehiclePricePerSecond = " + vehiclePricePerSecond + ", " +
                "vehicleX = " + vehicleX + ", " +
                "vehicleY = " + vehicleY + ", " +
                "maxSpeed = " + maxSpeed + ")";
    }
}