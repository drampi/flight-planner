package io.codelex.flightplanner;

import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class PublicTripsController {
    @Autowired
    private TripService tripService;

    @GetMapping("/flights/search")
    public ResponseEntity<List<Trip>> search(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) {
        List<Trip> fromTo = tripService.search(from, to);
        return new ResponseEntity<>(fromTo, HttpStatus.OK);
    }
    
    @PostMapping("/flights")
    public ResponseEntity<List<Trip>> findTrip(@RequestBody FindTripRequest request) {
        if (request.getTo() == null
                || request.getFrom() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (request.getTo().getCountry() == null
                || request.getTo().getCountry().length() == 0
                || request.getTo().getCity() == null
                || request.getTo().getCity().length() == 0
                || request.getTo().getAirport() == null
                || request.getTo().getAirport().length() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (request.getFrom().getCountry() == null
                || request.getFrom().getCountry().length() == 0
                || request.getFrom().getCity() == null
                || request.getFrom().getCity().length() == 0
                || request.getFrom().getAirport() == null
                || request.getFrom().getAirport().length() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if ((request.getDeparture() == null
                || request.getArrival() == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (request.getTo().equals(request.getFrom())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tripService.findTrip(request), HttpStatus.OK);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        if (tripService.findTripById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(tripService.findTripById(id), HttpStatus.OK);
    }
}
