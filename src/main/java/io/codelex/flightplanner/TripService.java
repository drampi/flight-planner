package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    boolean isTripPresent(AddTripRequest request) {
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
        trips.removeIf(trip -> trip.getId().equals(id));
        
      /*  for (Trip trip : trips) {
            if (trip.getId().equals(id)) {
                trips.remove(trip);
        
            } 
        }*/
    }

    void clearAll() {
        trips.clear();
    }

    Trip findTripById(Long id) {
        for (Trip trip : trips) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    List<Trip> findTrip(FindTripRequest request) {
        List<Trip> foundTrips = new ArrayList<>();
        
        for (Trip trip : trips) {
            if (trip.getFrom().equals(request.getFrom())
                    && trip.getTo().equals(request.getTo())
                    && trip.getDepartureTime().toLocalDate().equals(request.getDeparture())
                    && trip.getArrivalTime().toLocalDate().equals(request.getArrival())) {
                foundTrips.add(trip);
            }
        }
        return foundTrips;
    } 
      /*  Airport from = request.getFrom();
        Airport to = request.getTo();
        LocalDate departure = request.getDeparture();
        LocalDate arrival = request.getArrival();
        return trips.stream()
                .filter(it -> it.getFrom().equals(from))
                .filter(it -> it.getTo().equals(to))
                .filter(it -> it.getDepartureTime().toLocalDate().equals(departure))
                .filter(it -> it.getArrivalTime().toLocalDate().equals(arrival))
                .collect(Collectors.toList()); 
    }*/

    List<Trip> search(String from, String to) {
        List<Trip> foundTrips = new ArrayList<>();
        if (from == null
                || to == null) {
            return foundTrips;
        }

        from = StringUtils.trimAllWhitespace(from).toLowerCase();
        to = StringUtils.trimAllWhitespace(to).toLowerCase();

        for (Trip trip : trips) {
            if (trip.getFrom().getAirport().toLowerCase().contains(from)
                    && trip.getTo().getAirport().toLowerCase().contains(to)) {
                foundTrips.add(trip);
            } else if (
                    trip.getFrom().getCity().toLowerCase().contains(from)
                            && trip.getTo().getCity().toLowerCase().contains(to)) {
                foundTrips.add(trip);
            } else if (
                    trip.getFrom().getCountry().toLowerCase().contains(from)
                            && trip.getTo().getCountry().toLowerCase().contains(to)) {
                foundTrips.add(trip);
            }
        }
        return foundTrips;
    }

    List<Trip> findAll() {
        return null;
    }


}

