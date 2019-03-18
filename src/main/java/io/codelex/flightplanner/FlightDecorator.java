package io.codelex.flightplanner;

import io.codelex.flightplanner.api.*;
import io.codelex.flightplanner.weather.WeatherGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class FlightDecorator {
    private TripService tripService;
    private WeatherGateway weatherGateway;
    
    List<Trip> findTrip(FindTripRequest request) {
        return tripService.findTrip(request)
                .stream()
                .map(trip -> decorate(trip))
                .collect(Collectors.toList());
    }
    
    private TripWithWeather decorate(Trip trip) {
        Airport to = trip.getTo();
        Weather weather = weatherGateway.fetchForecast(
                to.getCity(),
                trip.getArrivalTime().toLocalDate()
        );
        return new TripWithWeather(
                trip.getId(),
                trip.getFrom(),
                to,
                trip.getCarrier(),
                trip.getDepartureTime(),
                trip.getArrivalTime()
        );
    }
}
