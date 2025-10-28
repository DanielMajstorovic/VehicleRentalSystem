package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "e_bike")
public class EBike {
    @Id
    @Column(name = "VEHICLE_VehicleID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VEHICLE_VehicleID", nullable = false)
    private Vehicle vehicle;

    @Column(name = "Autonomy", nullable = false)
    private Integer autonomy;

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

    public Integer getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Integer autonomy) {
        this.autonomy = autonomy;
    }

}