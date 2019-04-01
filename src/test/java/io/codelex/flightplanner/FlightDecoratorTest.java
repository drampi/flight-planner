package io.codelex.flightplanner;

import io.codelex.flightplanner.api.*;
import io.codelex.flightplanner.weather.ForecastCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class FlightDecoratorTest {
    private TripService tripService = mock(TripService.class);
    private ForecastCache forecastCache = mock(ForecastCache.class);

    private FlightDecorator decorator = new FlightDecorator(
            tripService,
            forecastCache
    );

    private LocalDate defaultDate = LocalDate.of(2019, 1, 1);
    private Weather defaultWeather = new Weather(0, 0, 0, "Snow");

    @Test
    void should_combine_results_from_service_and_gateway() {
        //given
        FindTripRequest req = new FindTripRequest(
                new Airport("LV", "Riga", "RIX"),
                new Airport("SWE", "Stockholm", "ARN"),
                defaultDate,
                defaultDate.plusDays(1)
        );
        List<Trip> tripsFromService = Collections.singletonList(
                new Trip(
                        1L,
                        new Airport("LV", "Riga", "RIX"),
                        new Airport("SWE", "Stockholm", "ARN"),
                        "Ryanair",
                        defaultDate.atStartOfDay(),
                        defaultDate.plusDays(1).atStartOfDay()
                )
        );
        when(tripService.findTrip(req))
                .thenReturn(tripsFromService
                );

        when(forecastCache.fetchForecast("Stockholm", defaultDate.plusDays(1)))
                .thenReturn(java.util.Optional.ofNullable(defaultWeather)
                );
        //when
        List<TripWithWeather> trips = decorator.findTrip(req);

        //then
        assertEquals(1, trips.size());

        //when
        TripWithWeather singleTrip = trips.get(0);

        //then
        Assertions.assertEquals(
                "Snow",
                singleTrip.getWeather().getCondition()
        );
    }
}