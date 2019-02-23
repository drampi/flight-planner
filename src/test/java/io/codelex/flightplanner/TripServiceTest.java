package io.codelex.flightplanner;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Trip;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class TripServiceTest {
    TripService service = new TripService();

    @Test
    public void should_be_able_to_add_flight() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip trip = service.addTrip(request);

        //then
        assertEqualsTripRequestProperly(request, trip);
    }
    @Test
    public void should_increment_id_when_adding_new_flight() {
        //given
        AddTripRequest request = new AddTripRequest(
                new Airport("Latvia", "Riga" , "RIX"),
                new Airport("Sweden", "Stockholm", "APN"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );

        //when
        Trip firstTrip = service.addTrip(request);
        Trip secondTrip = service.addTrip(request);

        //then
        assertEquals(firstTrip.getId() + 1, secondTrip.getId());
    }

    @Test
    public void should_be_able_to_get_added_trip_by_id() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip trip = service.addTrip(request);
        Trip result = service.findTripById(trip.getId());

        //then
        assertNotNull(result);

        //and
        assertEqualsTripRequestProperly(request, trip);
    }

    @Test
    public void should_not_be_able_to_add_duplicated_trip() {
        //given
        AddTripRequest request = createRequest();

        //when
        service.addTrip(request);

        //then
       assertThrows(IllegalStateException.class,
               () -> service.addTrip(request));

    }

    @Test
    public void should_get_no_result_if_nothing_added() {
    }

    @Test
    public void should_be_able_to_delete_flight_by_id() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip trip = service.addTrip(request);
        service.deleteTripById(trip.getId());

        //then
        trip = service.findTripById(trip.getId());
        assertNull(trip);
    }

    @Test
    public void should_be_able_to_delete_all_flights() {
        //given
        AddTripRequest request = createRequest();
        AddTripRequest request1 = new AddTripRequest(
                new Airport("Latvia", "Riga" , "RIX"),
                new Airport("SW", "SH", "VVV"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
        //when
        Trip trip = service.addTrip(request);
        Trip trip1 = service.addTrip(request1);
        service.clearAll();

        //then
        trip = service.findTripById(trip.getId());
        trip1 = service.findTripById(trip1.getId());
        assertNull(trip);
        assertNull(trip1);
    }

    @Test
    public void should_not_find_flights_when_nulls_passed() {
        //given
        AddTripRequest request = createRequest();

        //when
        service.addTrip(request);

        //then

        assertNull(service.search(null , null));
    }

    @Test
    public void should_find_flight_where_full_airport_name_from_passed() {
        //given
        AddTripRequest request = createRequest();

        //when
        Trip trip = service.addTrip(request);
        Trip result = service.search();

        //then
        assertNotNull(result);

        //and
        assertEqualsTripRequestProperly(request , trip);

    }


    @Test
    public void should_find_flight_where_full_country_from_passed() {

    }

    @Test
    public void should_find_flight_where_full_city_from_passed() {

    }
    @Test
    public void should_find_flight_where_partial_airport_name_from_passed() {

    }

    @Test
    public void should_find_flight_where_partial_country_from_passed() {

    }

    @Test
    public void should_find_flight_where_partial_city_from_passed() {

    }

    @Test
    public void should_find_flight_where_partial_lowercase_airport_name_from_passed() {

    }

    @Test
    public void should_find_flight_where_partial_uppercase_country_from_passed() {

    }

    @Test
    public void should_find_flight_where_partial_uppercase_city_from_passed() {

    }

    @Test
    public void should_find_flight_where_airport_name_from_with_space_at_the_end_passed() {

    }

    @Test
    public void should_find_flight_where_partial_country_from_with_space_at_the_end_passed() {

    }

    @Test
    public void should_find_flight_where_partial_city_from_with_space_at_the_end_passed() {

    }

    private AddTripRequest createRequest() {
         return new AddTripRequest(
                new Airport("Latvia", "Riga" , "RIX"),
                new Airport("Sweden", "Stockholm", "APN"),
                "Ryanair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
    }

    private void assertEqualsTripRequestProperly(AddTripRequest request, Trip trip) {
        assertEquals(trip.getFrom(), request.getFrom());
        assertEquals(trip.getTo(), request.getTo());
        assertEquals(trip.getCarrier(), request.getCarrier());
        assertEquals(trip.getDepartureTime(), request.getDepartureTime());
        assertEquals(trip.getArrivalTime(), request.getArrivalTime());
    }
}