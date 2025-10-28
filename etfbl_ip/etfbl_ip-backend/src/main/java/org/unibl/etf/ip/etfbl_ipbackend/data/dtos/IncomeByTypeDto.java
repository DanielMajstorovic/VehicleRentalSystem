package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.math.BigDecimal;

public class IncomeByTypeDto {
    private final String type;        // CAR | EBIKE | ESCOOTER | UNKNOWN
    private final BigDecimal income;

    public IncomeByTypeDto(String type, BigDecimal income) {
        this.type   = type;
        this.income = income;
    }
    public String getType()       { return type; }
    public BigDecimal getIncome() { return income; }
}