package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.enums.EmployeeRole;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "USER_UserID", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_UserID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private EmployeeRole role;

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

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

}