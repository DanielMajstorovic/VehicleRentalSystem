package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;

import java.math.BigDecimal;

public class VehicleMapDto {
    private Integer id;
    private String uid;
    private String model;
    private String manufacturerName;
    private String type; // "CAR", "EBIKE" ili "ESCOOTER"
    private VehicleStatus status;
    private BigDecimal pricePerSecond;
    private BigDecimal x;
    private BigDecimal y;

    public VehicleMapDto(BigDecimal y, BigDecimal x, BigDecimal pricePerSecond, VehicleStatus status, String type, String manufacturerName, String model, String uid, Integer id) {
        this.y = y;
        this.x = x;
        this.pricePerSecond = pricePerSecond;
        this.status = status;
        this.type = type;
        this.manufacturerName = manufacturerName;
        this.model = model;
        this.uid = uid;
        this.id = id;
    }

    public VehicleMapDto() {

    }

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
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
