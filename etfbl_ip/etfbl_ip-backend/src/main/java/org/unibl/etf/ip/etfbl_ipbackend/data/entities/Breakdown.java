package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "breakdown")
public class Breakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BreakdownID", nullable = false)
    private Integer id;

    @Column(name = "Description", nullable = false, length = 512)
    private String description;

    @Column(name = "BreakdownTime", nullable = false)
    private Instant breakdownTime;

    @Column(name = "RepairTime")
    private Instant repairTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VEHICLE_VehicleID", nullable = false)
    private Vehicle vehicleVehicleid;

    @ColumnDefault("0")
    @Column(name = "Deleted", nullable = false)
    private Byte deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getBreakdownTime() {
        return breakdownTime;
    }

    public void setBreakdownTime(Instant breakdownTime) {
        this.breakdownTime = breakdownTime;
    }

    public Instant getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Instant repairTime) {
        this.repairTime = repairTime;
    }

    public Vehicle getVehicleVehicleid() {
        return vehicleVehicleid;
    }

    public void setVehicleVehicleid(Vehicle vehicleVehicleid) {
        this.vehicleVehicleid = vehicleVehicleid;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

}