package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.enums.VehicleStatus;

import java.math.BigDecimal;

public class VehicleListDto {
    private Integer id;
    private String  uid;
    private String  model;
    private String  manufacturerName;
    private String  type;
    private VehicleStatus status;
    private BigDecimal pricePerSecond;

    public VehicleListDto(Integer id, String uid, String model, String type, String manufacturerName, VehicleStatus status, BigDecimal pricePerSecond) {
        this.id = id;
        this.uid = uid;
        this.model = model;
        this.type = type;
        this.manufacturerName = manufacturerName;
        this.status = status;
        this.pricePerSecond = pricePerSecond;
    }

    public VehicleListDto() {
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
}
