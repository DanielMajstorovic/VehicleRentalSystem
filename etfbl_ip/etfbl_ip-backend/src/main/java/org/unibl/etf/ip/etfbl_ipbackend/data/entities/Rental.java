package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalID", nullable = false)
    private Integer id;

    @Column(name = "StartTime", nullable = false)
    private Instant startTime;

    @Column(name = "StartX", nullable = false, precision = 9, scale = 6)
    private BigDecimal startX;

    @Column(name = "StartY", nullable = false, precision = 9, scale = 6)
    private BigDecimal startY;

    @Column(name = "EndTime")
    private Instant endTime;

    @Column(name = "EndX", precision = 9, scale = 6)
    private BigDecimal endX;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VEHICLE_VehicleID", nullable = false)
    private Vehicle vehicleVehicleid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CLIENT_USER_UserID", nullable = false)
    private Client clientUserUserid;

    @Column(name = "EndY", precision = 9, scale = 6)
    private BigDecimal endY;

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "DriversLicense", nullable = false, length = 25)
    private String driversLicense;

    @Column(name = "PaymentCard", length = 25)
    private String paymentCard;

    @Column(name = "TotalAmount", precision = 10, scale = 2)
    private BigDecimal totalAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getStartX() {
        return startX;
    }

    public void setStartX(BigDecimal startX) {
        this.startX = startX;
    }

    public BigDecimal getStartY() {
        return startY;
    }

    public void setStartY(BigDecimal startY) {
        this.startY = startY;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getEndX() {
        return endX;
    }

    public void setEndX(BigDecimal endX) {
        this.endX = endX;
    }

    public BigDecimal getEndY() {
        return endY;
    }

    public void setEndY(BigDecimal endY) {
        this.endY = endY;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public String getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(String paymentCard) {
        this.paymentCard = paymentCard;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Vehicle getVehicleVehicleid() {
        return vehicleVehicleid;
    }

    public void setVehicleVehicleid(Vehicle vehicleVehicleid) {
        this.vehicleVehicleid = vehicleVehicleid;
    }

    public Client getClientUserUserid() {
        return clientUserUserid;
    }

    public void setClientUserUserid(Client clientUserUserid) {
        this.clientUserUserid = clientUserUserid;
    }

}