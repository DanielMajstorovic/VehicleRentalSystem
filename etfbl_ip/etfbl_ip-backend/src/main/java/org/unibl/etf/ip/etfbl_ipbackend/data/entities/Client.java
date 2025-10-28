package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "USER_UserID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_UserID", nullable = false)
    private User user;

    @Column(name = "IDNumber", length = 25)
    private String iDNumber;

    @Column(name = "PassportNumber", length = 25)
    private String passportNumber;

    @Column(name = "Email", nullable = false, length = 128)
    private String email;

    @Column(name = "Phone", nullable = false, length = 25)
    private String phone;

    @ColumnDefault("0")
    @Column(name = "HasAvatarImage", nullable = false)
    private Byte hasAvatarImage;

    @ColumnDefault("100000.00")
    @Column(name = "Balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Byte getHasAvatarImage() {
        return hasAvatarImage;
    }

    public void setHasAvatarImage(Byte hasAvatarImage) {
        this.hasAvatarImage = hasAvatarImage;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}