package com.example.prenotazione_corse_autobus.dto;
import java.math.BigDecimal;

public class BuyReceipt {

    private Integer userId;
    private Integer tripId;
    private BigDecimal charge;   // addebito = price trip
    private BigDecimal balance;  // saldo residuo

    public BuyReceipt(Integer userId, Integer tripId, BigDecimal charge, BigDecimal balance) {
        this.userId = userId; this.tripId = tripId; this.charge = charge; this.balance = balance;
    }
    public Integer getUserId() { return userId; }
    public Integer getTripId() { return tripId; }
    public BigDecimal getCharge() { return charge; }
    public BigDecimal getBalance() { return balance; }

}
