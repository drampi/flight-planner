package io.codelex.flightplanner.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AddTripRequest {
    @Valid
    @NotNull
    private final Airport from;
    @Valid
    @NotNull
    private final Airport to;
    @NotEmpty
    private final String carrier;
    @Valid
    @NotNull
    private final LocalDateTime departureTime;
    @Valid
    @NotNull
    private final LocalDateTime arrivalTime;

    @JsonCreator
    public AddTripRequest(@JsonProperty("from") Airport from,
                          @JsonProperty("to") Airport to,
                          @JsonProperty("carrier") String carrier,
                          @JsonProperty("departureTime") LocalDateTime departureTime,
                          @JsonProperty("arrivalTime") LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
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
