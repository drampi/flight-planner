package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.FindTripRequest;
import io.codelex.flightplanner.api.Trip;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;

public class RepositoryFlightServiceTest {

    private FlightRecordRepository flightRecordRepository = Mockito.mock(FlightRecordRepository.class);
    private AirportRecordRepository airportRecordRepository = Mockito.mock(AirportRecordRepository.class);
    
    private RepositoryFlightService service = new RepositoryFlightService(flightRecordRepository, airportRecordRepository);

    @Test
    public void should_be_able_to_add_trip() {
        //given
        AddTripRequest request = new AddTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("UAE", "Dubai", "DXI"),
                "Turkish Airlines",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );
        Mockito.when(airportRecordRepository.save(Mockito.any()))
                .thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        
        //when
        Trip trip = service.addTrip(request);

        //expect
        Assertions.assertEquals(request.getCarrier(), trip.getCarrier());
    }
    
    @Test
    public void should_be_able_to_find_trip() {
        //given
        AddTripRequest request = new AddTripRequest(
                new Airport("Latvia", "Riga", "RIX"),
                new Airport("UAE", "Dubai", "DXI"),
                "Turkish Airlines",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );
        Mockito.when(airportRecordRepository.save(Mockito.any()))
                .thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        
        //when 
        Trip trip = service.addTrip(request);
        //expect
        Assertions.assertEquals(request.getFrom(), trip.getFrom());
    }
}