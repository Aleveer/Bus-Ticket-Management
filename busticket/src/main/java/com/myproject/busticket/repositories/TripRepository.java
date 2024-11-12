package com.myproject.busticket.repositories;

import com.myproject.busticket.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    @Query(value="SELECT t.*\n" +
            "FROM trip t\n" +
            "JOIN route_checkpoint r1 ON t.route_code = r1.route_code\n" +
            "JOIN route_checkpoint r2 ON t.route_code = r2.route_code\n" +
            "LEFT JOIN bus AS bus ON t.bus_id = bus.bus_id\n"+
            "LEFT JOIN booking AS b ON t.trip_id = b.trip_id\n" +
            "WHERE ((r1.checkpoint_city=:departure OR r1.checkpoint_province=:departure) AND r1.type = \"departure\")\n" +
            "AND ((r2.checkpoint_city=:destination OR r2.checkpoint_province=:destination) AND r2.type = \"drop_off\")\n" +
            "AND t.status = \"waiting\"\n" +
            "AND Date(t.departure_time) =:departureDate\n" +
            "GROUP BY t.trip_id\n" +
            "HAVING (bus.number_of_seat - IFNULL(SUM(b.number_of_seat), 0)) >=:numberOfTickets",
            nativeQuery=true)
    List<Trip> findTrip(@Param("departure") String departure,
                        @Param("destination") String destination,
                        @Param("departureDate") LocalDateTime departureDate,
                        @Param("numberOfTickets") int numberOfTickets);

    @Query()
    int findNumberOfSeatAvailableByTripId(int tripId);
}
