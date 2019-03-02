package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.TripService;
import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
@ConditionalOnProperty(prefix = "flight-planner", name ="store-type", havingValue = "in-memory")
class InMemoryTripService implements TripService {
    private final List<Trip> trips = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public synchronized Trip addTrip(AddTripRequest request) {
        if (isTripPresent(request)) {
            throw new IllegalStateException();
        }
        Trip trip = new Trip(
                id.incrementAndGet(),
                request.getFrom(),
                request.getTo(),
                request.getCarrier(),
                request.getDepartureTime(),
                request.getArrivalTime()
        );
        trips.add(trip);
        return trip;
    }

    @Override
    public synchronized boolean isTripPresent(AddTripRequest request) {
        for (Trip trip : trips) {
            if (trip.getFrom().getAirport().equals(request.getFrom().getAirport())
                    && trip.getTo().getAirport().equals(request.getTo().getAirport())
                    && trip.getCarrier().equals(request.getCarrier())
                    && trip.getDepartureTime().equals(request.getDepartureTime())
                    && trip.getArrivalTime().equals(request.getArrivalTime()))
                return true;
        }
        return false;
    }

    @Override
    public synchronized void deleteTripById(Long id) {
        trips.removeIf(trip -> trip.getId().equals(id));
    }

    @Override
    public synchronized void clearAll() {
        trips.clear();
    }

    @Override
    public synchronized Optional<Trip> findTripById(Long id) {
        for (Trip trip : trips) {
            if (trip.getId().equals(id)) {
                return Optional.of(trip);
            }
        }
        return null;
    }

    @Override
    public synchronized List<Trip> findTrip(FindTripRequest request) {
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

    @Override
    public synchronized List<Trip> search(@RequestParam String from, String to) {
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
}

