package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class TripService {
    private final List<Trip> trips = new ArrayList<>();

    Trip addTrip(AddTripRequest request) {
        Trip trip = new Trip(
                1L,
                request.getFrom(),
                request.getTo(),
                request.getCarrier()
        );

        trips.add(trip);
        return trip;
    }

    Trip search(String from, String to) {
        return null;
    }

    List<Trip> findAll() {
        return trips;
    }
}

