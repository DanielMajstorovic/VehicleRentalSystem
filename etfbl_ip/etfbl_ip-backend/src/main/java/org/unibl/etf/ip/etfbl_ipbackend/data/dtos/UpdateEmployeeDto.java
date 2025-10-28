package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.EmployeeRole;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Employee}
 */
public class UpdateEmployeeDto implements Serializable {
    @Size(max = 50)
    private String userPassword;
    @NotBlank
    @Size(max = 50)
    private String userFirstName;
    @NotBlank
    @Size(max = 50)
    private String userLastName;
    private EmployeeRole role;

    public UpdateEmployeeDto() {
    }

    public UpdateEmployeeDto(String userPassword, String userFirstName, String userLastName, EmployeeRole role) {
        this.userPassword = userPassword;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.role = role;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateEmployeeDto entity = (UpdateEmployeeDto) o;
        return Objects.equals(this.userPassword, entity.userPassword) &&
                Objects.equals(this.userFirstName, entity.userFirstName) &&
                Objects.equals(this.userLastName, entity.userLastName) &&
                Objects.equals(this.role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPassword, userFirstName, userLastName, role);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userPassword = " + userPassword + ", " +
                "userFirstName = " + userFirstName + ", " +
                "userLastName = " + userLastName + ", " +
                "role = " + role + ")";
    }
}