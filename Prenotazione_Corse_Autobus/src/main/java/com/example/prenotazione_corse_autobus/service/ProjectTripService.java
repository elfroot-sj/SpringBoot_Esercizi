package com.example.prenotazione_corse_autobus.service;

import com.example.prenotazione_corse_autobus.dto.ReceiptDto;
import com.example.prenotazione_corse_autobus.entity.Trip;
import com.example.prenotazione_corse_autobus.entity.TripRepository;
import com.example.prenotazione_corse_autobus.entity.User;
import com.example.prenotazione_corse_autobus.entity.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class ProjectTripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public ProjectTripService(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    /* ------- LIST & SEARCH ------- */
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> findByOriginAndDestination(String origin, String destination) {
        return tripRepository.findByOriginIgnoreCaseAndDestinationIgnoreCase(origin, destination);
    }

    /* ------- CREATE (ADMIN) ------- */
    public Trip createTrip(Trip t) {
        if (t.getOrigin() == null || t.getDestination() == null || t.getDepartureTime() == null || t.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields");
        }
        if (t.getPrice().signum() < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Price must be >= 0");
        }
        t.setPrice(t.getPrice().setScale(2, RoundingMode.HALF_UP));
        return tripRepository.save(t);
    }

    /* ------- BUY ------- */
    public ReceiptDto buyTrip(Integer userId, Integer tripId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));

        BigDecimal price = trip.getPrice().setScale(2, RoundingMode.HALF_UP);
        BigDecimal credit = user.getCredit().setScale(2, RoundingMode.HALF_UP);

        if (credit.compareTo(price) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient credit");
        }

        user.setCredit(credit.subtract(price));
        userRepository.save(user); // persiste il nuovo saldo

        return new ReceiptDto(
                user.getId(),
                trip.getId(),
                price,
                user.getCredit()
        );

    }
}
