package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

public class BreakdownsCountDto {
    private final String vehicleUid;
    private final Long   count;

    public BreakdownsCountDto(String vehicleUid, Long count) {
        this.vehicleUid = vehicleUid;
        this.count      = count;
    }
    public String getVehicleUid() { return vehicleUid; }
    public Long   getCount()      { return count; }
}
