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
    public ResponseEntity<List<Trip>> search(@RequestParam("from") String from, @RequestParam("to")String to) {
        List<Trip> fromTo = tripService.search(from, to);
        if (fromTo.isEmpty()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(fromTo , HttpStatus.OK);
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
        if (request.getTo().equals(request.getFrom())) {
            return new ResponseEntity(HttpStatus.OK);
        }
            return new ResponseEntity(tripService.findTrip(request), HttpStatus.OK);

    }

    @GetMapping("/flights/{id}")
    public Trip findTripById(@PathVariable Long id) {
        return tripService.findTripById(id);
    }
}
