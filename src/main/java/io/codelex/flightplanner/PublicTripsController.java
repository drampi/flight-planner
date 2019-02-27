package io.codelex.flightplanner;

import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
class PublicTripsController {
    @Autowired
    private TripService tripService;

    @GetMapping("/flights/search")
    public ResponseEntity<List<Trip>> search(@RequestParam("from") String from, @RequestParam("to") String to) {
        List<Trip> fromTo = tripService.search(from, to);
        if (tripService.search(from, to) == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(fromTo, HttpStatus.OK);
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
    public ResponseEntity<List<Trip>> findTrip(@Valid @RequestBody FindTripRequest request) {
        /*if (request.getTo().equals(request.getFrom())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else */if (request.getFrom() == null || request.getTo() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else if(tripService.findTrip(request) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (tripService.findTrip(request) == null
                || request.getTo() == null
                || request.getFrom() == null
                || request.getTo().getCountry() == null
                || request.getTo().getCountry().length() == 0
                || request.getTo().getCity() == null
                || request.getTo().getCity().length() == 0
                || request.getTo().getAirport() == null
                || request.getTo().getAirport().length() == 0
                || request.getFrom().getCountry() == null
                || request.getFrom().getCountry().length() == 0
                || request.getFrom().getCity() == null
                || request.getFrom().getCity().length() == 0
                || request.getFrom().getAirport() == null
                || request.getFrom().getAirport().length() == 0
                || request.getDeparture() == null
                || request.getArrival() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
        return new ResponseEntity(tripService.findTrip(request), HttpStatus.OK);

    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        if (tripService.findTripById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tripService.findTripById(id), HttpStatus.OK);
    }
}
