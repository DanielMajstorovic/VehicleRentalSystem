package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link org.unibl.etf.ip.etfbl_ipbackend.data.entities.Breakdown}
 */
public class BreakdownDto implements Serializable {
    private Integer id;
    private String description;
    private Instant breakdownTime;
    private Instant repairTime;

    public BreakdownDto() {
    }

    public BreakdownDto(Integer id, String description, Instant breakdownTime, Instant repairTime) {
        this.id = id;
        this.description = description;
        this.breakdownTime = breakdownTime;
        this.repairTime = repairTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getBreakdownTime() {
        return breakdownTime;
    }

    public void setBreakdownTime(Instant breakdownTime) {
        this.breakdownTime = breakdownTime;
    }

    public Instant getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Instant repairTime) {
        this.repairTime = repairTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreakdownDto entity = (BreakdownDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.breakdownTime, entity.breakdownTime) &&
                Objects.equals(this.repairTime, entity.repairTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, breakdownTime, repairTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "description = " + description + ", " +
                "breakdownTime = " + breakdownTime + ", " +
                "repairTime = " + repairTime + ")";
    }
}