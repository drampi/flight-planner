package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.TripService;
import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import io.codelex.flightplanner.repository.model.AirportRecord;
import io.codelex.flightplanner.repository.model.FlightRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
class RepositoryFlightService implements TripService {
    private final FlightRecordRepository flightRecordRepository;
    private final AirportRecordRepository airportRecordRepository;
    private final MapFlightRecordToFlight toFlight = new MapFlightRecordToFlight();

    RepositoryFlightService(FlightRecordRepository flightRecordRepository,
                            AirportRecordRepository airportRecordRepository) {
        this.flightRecordRepository = flightRecordRepository;
        this.airportRecordRepository = airportRecordRepository;
    }

    @Override
    public Trip addTrip(AddTripRequest request) {
        if (flightRecordRepository.isTripPresent(
                request.getFrom().getAirport(),
                request.getTo().getAirport(),
                request.getDepartureTime(),
                request.getArrivalTime(),
                request.getCarrier())) {
            throw new IllegalStateException();
        }

        FlightRecord flightRecord = new FlightRecord();

        flightRecord.setFrom(createOrGetAirport(request.getFrom()));
        flightRecord.setTo(createOrGetAirport(request.getTo()));
        flightRecord.setCarrier(request.getCarrier());
        flightRecord.setDepartureTime(request.getDepartureTime());
        flightRecord.setArrivalTime(request.getArrivalTime());

        flightRecordRepository.save(flightRecord);
        return toFlight.apply(flightRecord);
    }

    private AirportRecord createOrGetAirport(Airport airport) {
        return airportRecordRepository.findById(airport.getAirport())
                .orElseGet(() -> {
                    AirportRecord created = new AirportRecord(
                            airport.getAirport(),
                            airport.getCity(),
                            airport.getCountry()
                    );
                    return airportRecordRepository.save(created);
                });
    }

    @Override
    public boolean isTripPresent(AddTripRequest request) {
        return flightRecordRepository.isTripPresent(
                request.getFrom().getAirport(),
                request.getTo().getAirport(),
                request.getDepartureTime(),
                request.getArrivalTime(),
                request.getCarrier());
    }

    @Override
    public void deleteTripById(Long id) {
        try {
            flightRecordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

    @Override
    public void clearAll() {
        flightRecordRepository.deleteAll();
    }

    @Override
    public Trip findTripById(Long id) {
        return flightRecordRepository.findById(id)
                .map(toFlight)
                .orElse(null);
    }

    @Override
    public List<Trip> findTrip(FindTripRequest request) {
        return flightRecordRepository.findMatching(
                request.getFrom().getAirport(),
                request.getTo().getAirport(),
                request.getDeparture().atStartOfDay(),
                request.getDeparture().atStartOfDay().plusDays(1),
                request.getArrival().atStartOfDay(),
                request.getArrival().atStartOfDay().plusDays(1)
        ).stream()
                .map(toFlight)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> search(String from, String to) {
        if (from == null || from.isEmpty()) {
            return flightRecordRepository.searchFlightsTo(to)
                    .stream()
                    .map(toFlight)
                    .collect(Collectors.toList());
        }

        if (to == null || to.isEmpty()) {
            return flightRecordRepository.searchFlightsFrom(from)
                    .stream()
                    .map(toFlight)
                    .collect(Collectors.toList());
        }

        return flightRecordRepository.searchFlights(from, to)
                .stream()
                .map(toFlight)
                .collect(Collectors.toList());
    }
}
