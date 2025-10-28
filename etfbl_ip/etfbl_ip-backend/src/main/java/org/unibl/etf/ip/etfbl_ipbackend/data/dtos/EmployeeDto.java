package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import org.unibl.etf.ip.etfbl_ipbackend.data.enums.EmployeeRole;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Employee}
 */
public class EmployeeDto implements Serializable {
    private Integer id;
    private String userUsername;
    private String userFirstName;
    private String userLastName;
    private EmployeeRole role;

    public EmployeeDto() {
    }

    public EmployeeDto(Integer id, String userUsername, String userFirstName, String userLastName, EmployeeRole role) {
        this.id = id;
        this.userUsername = userUsername;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.role = role;
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
        EmployeeDto entity = (EmployeeDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userUsername, entity.userUsername) &&
                Objects.equals(this.userFirstName, entity.userFirstName) &&
                Objects.equals(this.userLastName, entity.userLastName) &&
                Objects.equals(this.role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userUsername, userFirstName, userLastName, role);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userUsername = " + userUsername + ", " +
                "userFirstName = " + userFirstName + ", " +
                "userLastName = " + userLastName + ", " +
                "role = " + role + ")";
    }
}