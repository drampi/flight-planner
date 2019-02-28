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
        if(tripService.isTripPresent(request)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else if(request.getFrom() == null
                || request.getTo() == null
                || request.getCarrier() == null
                || request.getTo().getAirport() == null
                || request.getTo().getCity() == null
                || request.getTo().getCountry() == null
                || request.getFrom().getCountry() == null
                || request.getFrom().getCity() == null
                || request.getFrom().getAirport() == null
                || request.getDepartureTime() == null
                || request.getArrivalTime() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(request.getTo().getAirport().equals("")
                || request.getTo().getCountry().equals("")
                || request.getTo().getCity().equals("")
                || request.getCarrier().equals("")
                || request.getFrom().getAirport().equals("")
                || request.getFrom().getCountry().equals("")
                || request.getFrom().getCity().equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(request.getFrom().equals(request.getTo())
                || request.getFrom().getAirport().toLowerCase().equals(request.getTo().getAirport().toLowerCase())
                || request.getFrom().getCountry().toLowerCase().equals(request.getTo().getCountry().toLowerCase())
                || request.getFrom().getCity().toLowerCase().equals(request.getTo().getCity().toLowerCase())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if ((request.getDepartureTime().isAfter(request.getArrivalTime()))
                ||  request.getDepartureTime().equals(request.getArrivalTime())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(tripService.addTrip(request), HttpStatus.CREATED);
        }
    

    @DeleteMapping("/flights/{id}")
    public void deleteTripById(@PathVariable("id") Long id) {
        tripService.deleteTripById(id); 
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable Long id) {
        if (tripService.findTripById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tripService.findTripById(id), HttpStatus.OK);
    }
}
