package com.example.prenotazione_corse_autobus.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    // ricerca per origine e destinazione
    List<Trip> findByOriginIgnoreCaseAndDestinationIgnoreCase(String origin, String destination);









    // ricerca per intervallo date
    //List<Trip> findByDepartureTimeBetween(LocalDateTime from, LocalDateTime to);


}
