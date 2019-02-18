package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/internal-api")
class InternalTripsController {
    @Autowired
    private TripService tripService;

    @PutMapping("/trips")
    public Trip addTrip(@RequestBody AddTripRequest request) {
        return tripService.addTrip(request);
    }

    @DeleteMapping("/trips/{id}")
    public void deleteTripById(@PathVariable Long id) {

    }
}
