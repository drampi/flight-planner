package io.codelex.flightplanner;

import io.codelex.flightplanner.api.*;
import io.codelex.flightplanner.weather.ForecastCache;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class FlightDecorator {
    private final TripService tripService;
    private final ForecastCache forecastCache;

    FlightDecorator(TripService tripService,
                    ForecastCache forecastCache) {
        this.tripService = tripService;
        this.forecastCache = forecastCache;
    }

    List<TripWithWeather> findTrip(FindTripRequest request) {
        return tripService.findTrip(request)
                .stream()
                .map(trip -> decorate(trip))
                .collect(Collectors.toList());
    }
    
    private TripWithWeather decorate(Trip trip) {
        Airport to = trip.getTo();
        Optional<Weather> weather = forecastCache.fetchForecast(
                to.getCity(),
                trip.getArrivalTime().toLocalDate()
        );
        return new TripWithWeather(
                trip.getId(),
                trip.getFrom(),
                to,
                trip.getCarrier(),
                trip.getDepartureTime(),
                trip.getArrivalTime(),
                weather.orElse(null)
        );
    }
}
