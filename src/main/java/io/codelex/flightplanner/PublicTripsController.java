package io.codelex.flightplanner;

import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PostMapping("/flights")
    public ResponseEntity<List<Trip>> findTrip(@RequestBody FindTripRequest request) {
        System.out.println(request.getFrom());
        return new ResponseEntity(request.getFrom(), HttpStatus.OK);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        return tripService.findTripById(id)
                .map(trip ->  new ResponseEntity<>(trip, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}