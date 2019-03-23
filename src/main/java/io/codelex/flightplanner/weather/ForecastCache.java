package io.codelex.flightplanner.weather;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.codelex.flightplanner.api.Weather;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.util.concurrent.TimeUnit.HOURS;

@Component
public class ForecastCache {
    private final WeatherGateway gateway;
    
    private final Cache<CacheKey, Weather> cache 
            = Caffeine.newBuilder()
            .expireAfterWrite(1, HOURS)
            .build();
    
    ForecastCache(WeatherGateway gateway) {
        this.gateway = gateway;
    }

    public Weather fetchForecast(String city,
                                 LocalDate date) {
        CacheKey key = new CacheKey(city, date);
        Weather weather = cache.getIfPresent(key);
        if (weather != null) {
            return weather;
        }
        weather = gateway.fetchForecast(city, date);
        cache.put(key, weather);
        return weather;
    }
    
    
}