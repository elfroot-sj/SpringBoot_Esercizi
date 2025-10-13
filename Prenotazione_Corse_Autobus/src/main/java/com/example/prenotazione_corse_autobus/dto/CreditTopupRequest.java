package com.example.prenotazione_corse_autobus.dto;
import java.math.BigDecimal;


public class CreditTopupRequest {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
