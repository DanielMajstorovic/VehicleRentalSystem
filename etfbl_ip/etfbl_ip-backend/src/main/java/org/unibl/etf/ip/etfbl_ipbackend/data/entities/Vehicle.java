package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VehicleID", nullable = false)
    private Integer id;

    @Column(name = "UID", nullable = false, length = 75)
    private String uid;

    @Column(name = "PurchasePrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "Model", nullable = false, length = 75)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANUFACTURER_ManufacturerID", nullable = false)
    private Manufacturer manufacturerManufacturerid;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private VehicleStatus status;

    @ColumnDefault("0")
    @Column(name = "Deleted", nullable = false)
    private Byte deleted;

    @Column(name = "PricePerSecond", nullable = false, precision = 12, scale = 8)
    private BigDecimal pricePerSecond;

    @Column(name = "X", nullable = false, precision = 9, scale = 6)
    private BigDecimal x;

    @Column(name = "Y", precision = 9, scale = 6)
    private BigDecimal y;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturerManufacturerid() {
        return manufacturerManufacturerid;
    }

    public void setManufacturerManufacturerid(Manufacturer manufacturerManufacturerid) {
        this.manufacturerManufacturerid = manufacturerManufacturerid;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getPricePerSecond() {
        return pricePerSecond;
    }

    public void setPricePerSecond(BigDecimal pricePerSecond) {
        this.pricePerSecond = pricePerSecond;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

}