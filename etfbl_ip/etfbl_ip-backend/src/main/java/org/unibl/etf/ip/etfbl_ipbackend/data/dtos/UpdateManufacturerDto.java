package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Manufacturer}
 */
public class UpdateManufacturerDto implements Serializable {
    @Size(max = 75)
    @NotBlank(message = "Name required.")
    private String name;
    @Size(max = 75)
    @NotBlank(message = "Country required.")
    private String country;
    @Size(max = 75)
    @NotBlank(message = "Address required.")
    private String address;
    @Size(max = 25)
    private String phone;
    @Size(max = 25)
    private String fax;
    @Size(max = 128)
    @Email
    @NotBlank(message = "Email required.")
    private String email;

    public UpdateManufacturerDto() {
    }

    public UpdateManufacturerDto(String name, String country, String address, String phone, String fax, String email) {
        this.name = name;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateManufacturerDto entity = (UpdateManufacturerDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.country, entity.country) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.fax, entity.fax) &&
                Objects.equals(this.email, entity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, address, phone, fax, email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "country = " + country + ", " +
                "address = " + address + ", " +
                "phone = " + phone + ", " +
                "fax = " + fax + ", " +
                "email = " + email + ")";
    }
}