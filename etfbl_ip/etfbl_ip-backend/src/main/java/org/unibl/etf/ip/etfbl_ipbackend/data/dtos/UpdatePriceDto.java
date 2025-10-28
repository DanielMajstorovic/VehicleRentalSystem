package org.unibl.etf.ip.etfbl_ipbackend.data.dtos;

import java.math.BigDecimal;

public class UpdatePriceDto {

    private BigDecimal pricePerSecond;

    public UpdatePriceDto() {

    }

    public UpdatePriceDto(BigDecimal pricePerSecond) {
        this.pricePerSecond = pricePerSecond;
    }

    public BigDecimal getPricePerSecond() {
        return pricePerSecond;
    }

    public void setPricePerSecond(BigDecimal pricePerSecond) {
        this.pricePerSecond = pricePerSecond;
    }
}
