package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    private final double temperature;
    private final int precipitation;
    private final double windSpeed;
    private final String condition;

    @JsonCreator
    public Weather(@JsonProperty("condition") double temperature,
                   @JsonProperty("precipitation") int precipitation,
                   @JsonProperty("windSpeed") double windSpeed,
                   @JsonProperty("condition") String condition) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCondition() {
        return condition;
    }
}
