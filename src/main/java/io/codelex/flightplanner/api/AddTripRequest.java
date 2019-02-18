package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTripRequest {
    private final Airport from;
    private final Airport to;
    private final String carrier;

    @JsonCreator
    public AddTripRequest(@JsonProperty("from") Airport from,
                          @JsonProperty("to") Airport to,
                          @JsonProperty("carrier") String carrier) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
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
