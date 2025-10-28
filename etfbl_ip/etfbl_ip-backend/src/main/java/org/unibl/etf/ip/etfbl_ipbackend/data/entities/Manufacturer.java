package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManufacturerID", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 75)
    private String name;

    @Column(name = "Country", nullable = false, length = 75)
    private String country;

    @Column(name = "Address", nullable = false, length = 75)
    private String address;

    @Column(name = "Phone", length = 25)
    private String phone;

    @Column(name = "Fax", length = 25)
    private String fax;

    @Column(name = "Email", nullable = false, length = 128)
    private String email;

    @ColumnDefault("0")
    @Column(name = "Deleted", nullable = false)
    private Byte deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

}