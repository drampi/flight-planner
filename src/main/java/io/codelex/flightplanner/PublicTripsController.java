package io.codelex.flightplanner;

import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
class PublicTripsController {
    @Autowired
    private TripService tripService;

    @GetMapping("/flights/search")
    public List<Trip> search(String from, String to) {
        return tripService.findAll();
    }

    /*
    POST http://localhost:8080/api/trips

{
    "from": "Riga",
    "to:": "Stockholm",
    "departure": "2019-02-27",
    "arrival": "2019-02-27"
}
     */
    @PostMapping("/trips")
    public ResponseEntity<List<Trip>> findTrip(@RequestBody FindTripRequest request) {
        System.out.println(request.getFrom());
        Trip trip1 = new Trip(
                1L,
                new Airport("Latvia" , "Riga", "RIX"),
                new Airport("Sweden", "Stockholm", "ARN"),
                "Norwegian"
        );
        Trip trip2 = new Trip(
                2L,
                new Airport("Latvia" , "Riga", "RIX"),
                new Airport("Dubai", "UAE", "DBX"),
                "Turkish Airlines"
        );
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        return new ResponseEntity<>(trips, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/trips/{id}")
    public Trip findTripById(@PathVariable Long id) {
        System.out.println(id);
        return new Trip(
                2L,
                new Airport("Latvia" , "Riga", "RIX"),
                new Airport("Dubai", "UAE", "DBX"),
                "Turkish Airlines"
        );

    }
}
