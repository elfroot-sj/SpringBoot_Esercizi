package com.example.prenotazione_corse_autobus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Origin is required")
    @Column(nullable = false, length = 100)
    private String origin;

    @NotBlank(message = "Destination is required")
    @Column(nullable = false, length = 100)
    private String destination;

    @NotNull(message = "Departure time is required")
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // es: 2025-10-20T14:30:00
    private LocalDateTime departureTime;

    @PositiveOrZero(message = "Price must not be negative")
    @NotNull(message = "Price is required")
    @Column(nullable = false, precision = 10, scale = 2) // DECIMAL(10,2)
    private BigDecimal price;

    // Getters & Setters (fluent opzionali)
    public Integer getId() { return id; }
    public Trip setId(Integer id) { this.id = id; return this; }

    public String getOrigin() { return origin; }
    public Trip setOrigin(String origin) { this.origin = origin; return this; }

    public String getDestination() { return destination; }
    public Trip setDestination(String destination) { this.destination = destination; return this; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public Trip setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; return this; }

    public BigDecimal getPrice() { return price; }
    public Trip setPrice(BigDecimal price) { this.price = price; return this; }
}
