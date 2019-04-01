package io.codelex.flightplanner.repository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airports")
public class AirportRecord {

    @Id
    private String airport;
    private String city;
    private String country;

    public AirportRecord(String airport, String city, String country) {
        this.airport = airport;
        this.city = city;
        this.country = country;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
