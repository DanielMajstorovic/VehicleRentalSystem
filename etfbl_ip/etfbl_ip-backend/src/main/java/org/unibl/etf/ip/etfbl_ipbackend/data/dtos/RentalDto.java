package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Rental;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link Rental}
 */
public class RentalDto implements Serializable {
    private Integer id;
    private Instant startTime;
    private BigDecimal startX;
    private BigDecimal startY;
    private Instant endTime;
    private BigDecimal endX;
    private BigDecimal endY;
    private Integer duration;
    private String driversLicense;
    private String paymentCard;
    private BigDecimal totalAmount;
    private String clientUserFirstName;
    private String clientUserLastName;

    public RentalDto() {
    }

    public RentalDto(Integer id, Instant startTime, BigDecimal startX, BigDecimal startY, Instant endTime, BigDecimal endX, BigDecimal endY, Integer duration, String driversLicense, String paymentCard, BigDecimal totalAmount, String clientUserFirstName, String clientUserLastName) {
        this.id = id;
        this.startTime = startTime;
        this.startX = startX;
        this.startY = startY;
        this.endTime = endTime;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.driversLicense = driversLicense;
        this.paymentCard = paymentCard;
        this.totalAmount = totalAmount;
        this.clientUserFirstName = clientUserFirstName;
        this.clientUserLastName = clientUserLastName;
    }

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

    public String getClientUserFirstName() {
        return clientUserFirstName;
    }

    public void setClientUserFirstName(String clientUserFirstName) {
        this.clientUserFirstName = clientUserFirstName;
    }

    public String getClientUserLastName() {
        return clientUserLastName;
    }

    public void setClientUserLastName(String clientUserLastName) {
        this.clientUserLastName = clientUserLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDto entity = (RentalDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.startTime, entity.startTime) &&
                Objects.equals(this.startX, entity.startX) &&
                Objects.equals(this.startY, entity.startY) &&
                Objects.equals(this.endTime, entity.endTime) &&
                Objects.equals(this.endX, entity.endX) &&
                Objects.equals(this.endY, entity.endY) &&
                Objects.equals(this.duration, entity.duration) &&
                Objects.equals(this.driversLicense, entity.driversLicense) &&
                Objects.equals(this.paymentCard, entity.paymentCard) &&
                Objects.equals(this.totalAmount, entity.totalAmount) &&
                Objects.equals(this.clientUserFirstName, entity.clientUserFirstName) &&
                Objects.equals(this.clientUserLastName, entity.clientUserLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, startX, startY, endTime, endX, endY, duration, driversLicense, paymentCard, totalAmount, clientUserFirstName, clientUserLastName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "startTime = " + startTime + ", " +
                "startX = " + startX + ", " +
                "startY = " + startY + ", " +
                "endTime = " + endTime + ", " +
                "endX = " + endX + ", " +
                "endY = " + endY + ", " +
                "duration = " + duration + ", " +
                "driversLicense = " + driversLicense + ", " +
                "paymentCard = " + paymentCard + ", " +
                "totalAmount = " + totalAmount + ", " +
                "clientUserFirstName = " + clientUserFirstName + ", " +
                "clientUserLastName = " + clientUserLastName + ")";
    }
}