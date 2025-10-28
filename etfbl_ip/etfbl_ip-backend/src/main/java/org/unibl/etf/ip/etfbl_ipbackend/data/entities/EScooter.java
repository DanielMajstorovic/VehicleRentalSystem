package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "e_scooter")
public class EScooter {
    @Id
    @Column(name = "VEHICLE_VehicleID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VEHICLE_VehicleID", nullable = false)
    private Vehicle vehicle;

    @Column(name = "MaxSpeed", nullable = false)
    private Integer maxSpeed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}