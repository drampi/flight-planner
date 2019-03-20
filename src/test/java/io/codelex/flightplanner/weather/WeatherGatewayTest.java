package io.codelex.flightplanner.weather;

import io.codelex.flightplanner.api.Weather;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeatherGatewayTest {
    private WeatherGateway weatherGateway = new WeatherGateway();
    
    @Test
    void fetchForecast() {
        Weather weather = weatherGateway.fetchForecast("Riga", LocalDate.now());

        System.out.println(weather.getCondition());
        System.out.println(weather.getTemperature() + "Celsius");
        System.out.println(weather.getWindSpeed() + "km/h");
        System.out.println(weather.getPrecipitation() + "milimeters of rain");
        assertTrue(!weather.getCondition().isEmpty());
    }
}