package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.math.BigDecimal;

public class IncomePerDayDto {

    private Integer day;
    private BigDecimal income;

    public IncomePerDayDto() {}

    public IncomePerDayDto(Integer day, BigDecimal totalIncome) {
        this.day = day;
        this.income = totalIncome;
    }

    public Integer        getDay()    { return day; }
    public BigDecimal getIncome() { return income; }

    public void setIncome(BigDecimal income) { this.income = income; }
    public void setDay(Integer day) { this.day = day; }
}
