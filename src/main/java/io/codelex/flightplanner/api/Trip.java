package io.codelex.flightplanner.api;

public class Trip {

    private Long id;
    private Airport from;
    private Airport to;
    private String carrier;

    public Trip(Long id,
                Airport from,
                Airport to,
                String carrier) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
    }

    public Long getId() {
        return id;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }
}
