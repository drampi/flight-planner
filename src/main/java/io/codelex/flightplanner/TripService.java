package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class TripService {
    private final List<Trip> trips = new ArrayList<>();
    private Long sequence = 0L;


    Trip addTrip(AddTripRequest request) {
        Trip trip = new Trip(
                sequence++,
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime()
        );
        if (isTripPresent(request)) {
            throw new IllegalStateException();
        } else {
            trips.add(trip);
        }
       return trip;
    }

    private boolean isTripPresent(AddTripRequest request) {
        for (Trip trip : trips) {
            if (trip.getFrom().equals(request.getFrom())
                && trip.getTo().equals(request.getTo())
                && trip.getCarrier().equals(request.getCarrier())
                && trip.getDepartureTime().equals(request.getDepartureTime())
                && trip.getArrivalTime().equals(request.getArrivalTime()))
                return true;
        }
        return false;
    }

    void deleteTripById(Long id) {
        for (Trip trip:trips) {
            if (trip.getId().equals(id)) {
                trips.remove(trip);
                break;
            }
        }
    }

    void clearAll() {
        trips.clear();
    }

    public Trip findTripById(Long id) {
        for (Trip trip: trips) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    public List<Trip> findTrip(FindTripRequest request) {
        return null;
    }

    List<Trip> search(String from, String to) {
        List<Trip> foundTrips = new ArrayList<>();
        if (from == null
                || to == null) {
            return null;
        }

        for (Trip trip : trips) {
            if (trip.getFrom().getCity().equals(from)
            && trip.getTo().getCity().equals(to)) {
                foundTrips.add(trip);
            }
        }
        return foundTrips;
    }

    List<Trip> findAll() {
        return null;
    }


}

