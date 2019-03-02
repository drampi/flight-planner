package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.repository.model.FlightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRecordRepository extends JpaRepository<FlightRecord, Long> {

    @Query("select flight from FlightRecord flight where "
            + "lower(flight.from.airport) = lower(:from) "
            + " or lower(flight.to.airport) = lower(:to) "
            + " or lower(flight.from.city) = lower(:from) "
            + " or lower(flight.to.city) = lower(:to) "
            + " or lower(flight.from.country) = lower(:from ) "
            + " or lower(flight.to.country) = lower(:to)")
    List<FlightRecord> searchFlights(@Param("from") String from,
                                     @Param("to") String to);
}
