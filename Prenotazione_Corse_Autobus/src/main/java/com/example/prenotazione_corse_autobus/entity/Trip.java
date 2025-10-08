package com.example.prenotazione_corse_autobus.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The origin parameter must not be blank!")
    private String origin;

    @NotNull(message = "The destination parameter must not be blank!")
    private String destination;

    @NotNull(message = "The Departure Time parameter must not be blank!")
    private LocalDateTime departureTime;

    //il prezzo non può essere negativo
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit must not be negative!")
    @NotNull(message = "The price parameter must not be blank!")
    private BigDecimal price;


    public Integer getId() {
        return id;
    }

    public Trip setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrigin() {return origin;}
    public Trip setOrigin(String origin) {this.origin = origin; return this;}

    public String getDestination() {return destination;}
    public Trip setDestination(String destination) {this.destination = destination; return this;}

    public LocalDateTime getDepartureTime() {return departureTime;}
    public Trip setDestination(LocalDateTime departureTime) {this.departureTime = departureTime; return this;}

    public BigDecimal getPrice() {return price;}
    public Trip setPrice(BigDecimal price) {this.price = price; return this;}



}
