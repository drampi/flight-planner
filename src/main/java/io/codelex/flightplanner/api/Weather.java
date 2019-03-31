package io.codelex.flightplanner.api;

public class Weather {
    private final double temperature;
    private final int precipitation;
    private final double windSpeed;
    private final String condition;

    public Weather(double temperature,
                   int precipitation,
                   double windSpeed,
                   String condition) {
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
