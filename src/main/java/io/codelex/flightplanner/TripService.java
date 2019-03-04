package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import java.util.List;

public interface TripService {
    Trip addTrip(AddTripRequest request);

    boolean isTripPresent(AddTripRequest request);

    void deleteTripById(Long id);

    void clearAll();

    Trip findTripById(Long id);

    List<Trip> findTrip(FindTripRequest request);

    List<Trip> search(String from, String to);


}
