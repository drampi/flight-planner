package io.codelex.flightplanner;

public class Flight {
    private final long id;
    private final String country;
    private final String city;
    private final String airport;


    public Flight(long id, String country, String city, String airport) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAirport() {
        return airport;
    }
}
