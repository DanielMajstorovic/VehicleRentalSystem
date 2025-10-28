package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Breakdown;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Breakdown}
 */
public class CreateBreakdownDto implements Serializable {
    private String description;
    private Integer vehicleId;

    public CreateBreakdownDto() {
    }

    public CreateBreakdownDto(String description, Integer vehicleId) {
        this.description = description;
        this.vehicleId = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBreakdownDto entity = (CreateBreakdownDto) o;
        return Objects.equals(this.description, entity.description) &&
                Objects.equals(this.vehicleId, entity.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, vehicleId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "description = " + description + ", " +
                "vehicleId = " + vehicleId + ")";
    }
}