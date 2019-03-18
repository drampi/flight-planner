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
        if (isRequestNull(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (isGetToLengthEmpty(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (request.getCarrier().length() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (isGetFromLengthEmpty(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (request.getFrom().equals(request.getTo())
                || request.getFrom().getAirport().toLowerCase().equals(request.getTo().getAirport().toLowerCase())
                || request.getFrom().getCity().toLowerCase().equals(request.getTo().getCity().toLowerCase())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if ((request.getDepartureTime().isAfter(request.getArrivalTime()))
                || request.getDepartureTime().equals(request.getArrivalTime())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (tripService.isTripPresent(request)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(tripService.addTrip(request), HttpStatus.CREATED);
    }

    private boolean isGetFromLengthEmpty(AddTripRequest request) {
        return request.getFrom().getAirport().length() == 0
                || request.getFrom().getCountry().length() == 0
                || request.getFrom().getCity().length() == 0;
    }

    private boolean isGetToLengthEmpty(AddTripRequest request) {
        return request.getTo().getAirport().length() == 0
                || request.getTo().getCountry().length() == 0
                || request.getTo().getCity().length() == 0;
    }

    private boolean isRequestNull(AddTripRequest request) {
        return request.getFrom() == null
                || request.getTo() == null
                || request.getCarrier() == null
                || request.getTo().getAirport() == null
                || request.getTo().getCity() == null
                || request.getTo().getCountry() == null
                || request.getFrom().getCountry() == null
                || request.getFrom().getCity() == null
                || request.getFrom().getAirport() == null
                || request.getDepartureTime() == null
                || request.getArrivalTime() == null;
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
