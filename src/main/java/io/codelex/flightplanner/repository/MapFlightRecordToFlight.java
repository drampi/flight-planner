package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Trip;
import io.codelex.flightplanner.repository.model.FlightRecord;

import java.util.function.Function;

class MapFlightRecordToFlight implements Function<FlightRecord, Trip> {
    @Override
    public Trip apply(FlightRecord flightRecord) {
        return new Trip(
                flightRecord.getId(),
                new Airport(
                        flightRecord.getFrom().getCountry(),
                        flightRecord.getFrom().getCity(),
                        flightRecord.getFrom().getAirport()
                ),
                new Airport(
                        flightRecord.getTo().getCountry(),
                        flightRecord.getTo().getCity(),
                        flightRecord.getTo().getAirport()
                ),
                flightRecord.getCarrier(),
                flightRecord.getDepartureTime(),
                flightRecord.getArrivalTime()
        );
    }
}
