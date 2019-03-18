package io.codelex.flightplanner.weather;

import io.codelex.flightplanner.api.Weather;
import io.codelex.flightplanner.weather.ForecastResponse.Day;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherGateway {
    private final RestTemplate restTemplate = new RestTemplate();
    
    public Weather fetchForecast(String city,
                                 LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
        String url = "https://api.apixu.com/v1/forecast.json?key=00ab253837da498b9bc150014190903&q=" + city + "&dt=" + formattedDate;
        ForecastResponse response = restTemplate.getForObject(url, ForecastResponse.class);
        
        Day day = response.getForecast()
                .getForecastDays().get(0).getDay();
        
        return new Weather(
                day.getAverageTemperature(),
                day.getTotalPrecipitation(),
                day.getMaxWind(),
                day.getCondition().getText()
        );
    }
}
