package com.example.prenotazione_corse_autobus.exceptions;

public class InsufficientCreditException extends RuntimeException {
    public InsufficientCreditException(String message) {
        super(message);
    }
}
