package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal-api")
class InternalTripsController {
    @Autowired
    private TripService tripService;

    @PutMapping("/flights")
    public ResponseEntity<Trip> addTrip(@RequestBody AddTripRequest request) {
        return new ResponseEntity(tripService.addTrip(request),HttpStatus.CREATED);
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Trip> deleteTripById(@PathVariable("id") Long id) {
        tripService.deleteTripById(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }
}
