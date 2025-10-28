package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Rental}
 */
public class RentalDetailsDto implements Serializable {
    private Integer id;
    private Instant startTime;
    private BigDecimal startX;
    private BigDecimal startY;
    private Instant endTime;
    private BigDecimal endX;
    private Integer vehicleId;
    private String vehicleUid;
    private String vehicleModel;
    private Integer vehicleManufacturerId;
    private String vehicleManufacturerName;
    private Integer clientId;
    private Integer clientUserId;
    private String clientUserUsername;
    private String clientUserFirstName;
    private String clientUserLastName;
    private String clientIDNumber;
    private String clientPassportNumber;
    private String clientEmail;
    private String clientPhone;
    private BigDecimal endY;
    private String driversLicense;
    private String paymentCard;
    private BigDecimal totalAmount;

    public RentalDetailsDto() {
    }

    public RentalDetailsDto(Integer id, Instant startTime, BigDecimal startX, BigDecimal startY, Instant endTime, BigDecimal endX, Integer vehicleId, String vehicleUid, String vehicleModel, Integer vehicleManufacturerId, String vehicleManufacturerName, Integer clientId, Integer clientUserId, String clientUserUsername, String clientUserFirstName, String clientUserLastName, String clientIDNumber, String clientPassportNumber, String clientEmail, String clientPhone, BigDecimal endY, String driversLicense, String paymentCard) {
        this.id = id;
        this.startTime = startTime;
        this.startX = startX;
        this.startY = startY;
        this.endTime = endTime;
        this.endX = endX;
        this.vehicleId = vehicleId;
        this.vehicleUid = vehicleUid;
        this.vehicleModel = vehicleModel;
        this.vehicleManufacturerId = vehicleManufacturerId;
        this.vehicleManufacturerName = vehicleManufacturerName;
        this.clientId = clientId;
        this.clientUserId = clientUserId;
        this.clientUserUsername = clientUserUsername;
        this.clientUserFirstName = clientUserFirstName;
        this.clientUserLastName = clientUserLastName;
        this.clientIDNumber = clientIDNumber;
        this.clientPassportNumber = clientPassportNumber;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.endY = endY;
        this.driversLicense = driversLicense;
        this.paymentCard = paymentCard;
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

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleUid() {
        return vehicleUid;
    }

    public void setVehicleUid(String vehicleUid) {
        this.vehicleUid = vehicleUid;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Integer getVehicleManufacturerId() {
        return vehicleManufacturerId;
    }

    public void setVehicleManufacturerId(Integer vehicleManufacturerId) {
        this.vehicleManufacturerId = vehicleManufacturerId;
    }

    public String getVehicleManufacturerName() {
        return vehicleManufacturerName;
    }

    public void setVehicleManufacturerName(String vehicleManufacturerName) {
        this.vehicleManufacturerName = vehicleManufacturerName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Integer clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getClientUserUsername() {
        return clientUserUsername;
    }

    public void setClientUserUsername(String clientUserUsername) {
        this.clientUserUsername = clientUserUsername;
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

    public String getClientIDNumber() {
        return clientIDNumber;
    }

    public void setClientIDNumber(String clientIDNumber) {
        this.clientIDNumber = clientIDNumber;
    }

    public String getClientPassportNumber() {
        return clientPassportNumber;
    }

    public void setClientPassportNumber(String clientPassportNumber) {
        this.clientPassportNumber = clientPassportNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public BigDecimal getEndY() {
        return endY;
    }

    public void setEndY(BigDecimal endY) {
        this.endY = endY;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDetailsDto entity = (RentalDetailsDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.startTime, entity.startTime) &&
                Objects.equals(this.startX, entity.startX) &&
                Objects.equals(this.startY, entity.startY) &&
                Objects.equals(this.endTime, entity.endTime) &&
                Objects.equals(this.endX, entity.endX) &&
                Objects.equals(this.vehicleId, entity.vehicleId) &&
                Objects.equals(this.vehicleUid, entity.vehicleUid) &&
                Objects.equals(this.vehicleModel, entity.vehicleModel) &&
                Objects.equals(this.vehicleManufacturerId, entity.vehicleManufacturerId) &&
                Objects.equals(this.vehicleManufacturerName, entity.vehicleManufacturerName) &&
                Objects.equals(this.clientId, entity.clientId) &&
                Objects.equals(this.clientUserId, entity.clientUserId) &&
                Objects.equals(this.clientUserUsername, entity.clientUserUsername) &&
                Objects.equals(this.clientUserFirstName, entity.clientUserFirstName) &&
                Objects.equals(this.clientUserLastName, entity.clientUserLastName) &&
                Objects.equals(this.clientIDNumber, entity.clientIDNumber) &&
                Objects.equals(this.clientPassportNumber, entity.clientPassportNumber) &&
                Objects.equals(this.clientEmail, entity.clientEmail) &&
                Objects.equals(this.clientPhone, entity.clientPhone) &&
                Objects.equals(this.endY, entity.endY) &&
                Objects.equals(this.driversLicense, entity.driversLicense) &&
                Objects.equals(this.paymentCard, entity.paymentCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, startX, startY, endTime, endX, vehicleId, vehicleUid, vehicleModel, vehicleManufacturerId, vehicleManufacturerName, clientId, clientUserId, clientUserUsername, clientUserFirstName, clientUserLastName, clientIDNumber, clientPassportNumber, clientEmail, clientPhone, endY, driversLicense, paymentCard);
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
                "vehicleId = " + vehicleId + ", " +
                "vehicleUid = " + vehicleUid + ", " +
                "vehicleModel = " + vehicleModel + ", " +
                "vehicleManufacturerId = " + vehicleManufacturerId + ", " +
                "vehicleManufacturerName = " + vehicleManufacturerName + ", " +
                "clientId = " + clientId + ", " +
                "clientUserId = " + clientUserId + ", " +
                "clientUserUsername = " + clientUserUsername + ", " +
                "clientUserFirstName = " + clientUserFirstName + ", " +
                "clientUserLastName = " + clientUserLastName + ", " +
                "clientIDNumber = " + clientIDNumber + ", " +
                "clientPassportNumber = " + clientPassportNumber + ", " +
                "clientEmail = " + clientEmail + ", " +
                "clientPhone = " + clientPhone + ", " +
                "endY = " + endY + ", " +
                "driversLicense = " + driversLicense + ", " +
                "paymentCard = " + paymentCard + ")";
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}