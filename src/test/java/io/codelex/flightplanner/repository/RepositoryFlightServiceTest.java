package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.api.AddTripRequest;
import io.codelex.flightplanner.api.Airport;
import io.codelex.flightplanner.api.Trip;
import io.codelex.flightplanner.repository.model.AirportRecord;
import io.codelex.flightplanner.repository.model.FlightRecord;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Mockito.when(flightRecordRepository.isTripPresent(
                request.getFrom().getAirport(),
                request.getTo().getAirport(),
                request.getDepartureTime(),
                request.getArrivalTime(),
                request.getCarrier()
        )).thenReturn(true);
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> service.addTrip(request));
    }
    
    @Test
    public void should_find_flight_by_id() {
        //given
        FlightRecord flightRecord = new FlightRecord();
        flightRecord.setId(1L);
        flightRecord.setFrom(
                new AirportRecord(
                        "RIX",
                        "Riga",
                        "Latvia"
                )
        );
        flightRecord.setTo(
                new AirportRecord(
                        "DXI",
                        "Dubai",
                        "UAE"
                )
        );
        flightRecord.setCarrier("Turkish Airlines");
        flightRecord.setDepartureTime(LocalDateTime.of(2019, 1, 1, 1, 1));
        flightRecord.setDepartureTime(LocalDateTime.of(2019, 1, 1, 1, 1).plusHours(1));
        
        Optional<FlightRecord> req = Optional.of(flightRecord);
        
        Mockito.when(flightRecordRepository.findById(1L))
                .thenReturn(req);
        //when
        
        Trip result = service.findTripById(1L);
        
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getFrom().getCity(), result.getFrom().getCity());
    }
}