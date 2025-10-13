package com.example.prenotazione_corse_autobus.dto;
import java.math.BigDecimal;

public class ReceiptDto {
    private Integer userId;
    private Integer tripId;
    private BigDecimal debit;
    private BigDecimal remainingBalance;

    public ReceiptDto(Integer userId, Integer tripId, BigDecimal debit, BigDecimal remainingBalance) {
        this.userId = userId;
        this.tripId = tripId;
        this.debit = debit;
        this.remainingBalance = remainingBalance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
