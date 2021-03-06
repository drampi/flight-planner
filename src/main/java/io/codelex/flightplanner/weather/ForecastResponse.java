package io.codelex.flightplanner.weather;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ForecastResponse {
    private final Forecast forecast;

    Forecast getForecast() {
        return forecast;
    }

    @JsonCreator
    public ForecastResponse(@JsonProperty("forecast") Forecast forecast) {
        this.forecast = forecast;
    }

    public static class Forecast {
        private final List<ForecastDay> forecastDays;

        List<ForecastDay> getForecastDays() {
            return forecastDays;
        }
        @JsonCreator
        public Forecast(@JsonProperty("forecastday") List<ForecastDay> forecastDays) {
            this.forecastDays = forecastDays;
        }
    }
    
    public static class ForecastDay {
        private final Day day;
        
        @JsonCreator
        public ForecastDay(@JsonProperty("day") Day day) {
            this.day = day;
        }

        Day getDay() {
            return day;
        }
    }
    
    public static class Day {
        //avgtemp_c
        private final double averageTemperature;
        //maxwind_kph
        private final double maxWind;
        //totalprecip_mm
        private final int totalPrecipitation;
        private final Condition condition;

        double getAverageTemperature() {
            return averageTemperature;
        }

        double getMaxWind() {
            return maxWind;
        }

        int getTotalPrecipitation() {
            return totalPrecipitation;
        }

        Condition getCondition() {
            return condition;
        }

        @JsonCreator
        public Day(@JsonProperty("avgtemp_c") double averageTemperature, 
                   @JsonProperty("maxwind_kph") double maxWind, 
                   @JsonProperty("totalprecip_mm") int totalPrecipitation, 
                   @JsonProperty("condition") Condition condition) {
            this.averageTemperature = averageTemperature;
            this.maxWind = maxWind;
            this.totalPrecipitation = totalPrecipitation;
            this.condition = condition;
        }
    }
    
    public static class Condition {
        private final String text;
        
        @JsonCreator
        public Condition(@JsonProperty("text") String text) {
            this.text = text;
        }

        String getText() {
            return text;
        }
    }
}
