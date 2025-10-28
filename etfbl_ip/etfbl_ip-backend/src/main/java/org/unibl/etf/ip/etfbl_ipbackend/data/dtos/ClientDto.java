package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Client}
 */
public class ClientDto implements Serializable {
    private Integer id;
    private String userUsername;
    private String userFirstName;
    private String userLastName;
    private Byte userDeleted;
    private String iDNumber;
    private String passportNumber;
    private String email;
    private String phone;
    private BigDecimal balance;

    public ClientDto() {
    }

    public ClientDto(Integer id, String userUsername, String userFirstName, String userLastName, Byte userDeleted, String iDNumber, String passportNumber, String email, String phone, BigDecimal balance) {
        this.id = id;
        this.userUsername = userUsername;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userDeleted = userDeleted;
        this.iDNumber = iDNumber;
        this.passportNumber = passportNumber;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Byte getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(Byte userDeleted) {
        this.userDeleted = userDeleted;
    }

    public String getIDNumber() {
        return iDNumber;
    }

    public void setIDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto entity = (ClientDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userUsername, entity.userUsername) &&
                Objects.equals(this.userFirstName, entity.userFirstName) &&
                Objects.equals(this.userLastName, entity.userLastName) &&
                Objects.equals(this.userDeleted, entity.userDeleted) &&
                Objects.equals(this.iDNumber, entity.iDNumber) &&
                Objects.equals(this.passportNumber, entity.passportNumber) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.balance, entity.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userUsername, userFirstName, userLastName, userDeleted, iDNumber, passportNumber, email, phone, balance);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userUsername = " + userUsername + ", " +
                "userFirstName = " + userFirstName + ", " +
                "userLastName = " + userLastName + ", " +
                "userDeleted = " + userDeleted + ", " +
                "iDNumber = " + iDNumber + ", " +
                "passportNumber = " + passportNumber + ", " +
                "email = " + email + ", " +
                "phone = " + phone + ", " +
                "balance = " + balance + ")";
    }
}