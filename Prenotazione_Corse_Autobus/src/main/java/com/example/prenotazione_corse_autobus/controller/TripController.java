package com.example.prenotazione_corse_autobus.controller;

import com.example.prenotazione_corse_autobus.dto.BuyReceipt;
import com.example.prenotazione_corse_autobus.dto.BuyRequest;
import com.example.prenotazione_corse_autobus.dto.ReceiptDto;
import com.example.prenotazione_corse_autobus.entity.Trip;
import com.example.prenotazione_corse_autobus.service.ProjectTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TripController {

    @Autowired
    private ProjectTripService tripService;

    // GET http://localhost:8082/trips  (PUBBLICO)
    @GetMapping(path = "/trips")
    public @ResponseBody List<Trip> getTrips(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination) {

        if (origin != null && destination != null) {
            return tripService.findByOriginAndDestination(origin.trim(), destination.trim());
        }
        return tripService.getAllTrips();
    }

    // POST http://localhost:8082/trips   (ADMIN)
    @PostMapping("/trips")
    public @ResponseBody Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }

    // POST http://localhost:8082/trips/{tripId}/buy (AUTENTICATO)
    @PostMapping(path = "/trips/{tripId}/buy")
    public @ResponseBody ResponseEntity<ReceiptDto> buyTrip(
            @PathVariable Integer tripId,
            @RequestBody BuyRequest req) {

        if (req.getUserId() == null) {
            return ResponseEntity.badRequest().build(); // 400
        }
        ReceiptDto receipt = tripService.buyTrip(req.getUserId(), tripId);
        return ResponseEntity.ok(receipt);
    }
}
