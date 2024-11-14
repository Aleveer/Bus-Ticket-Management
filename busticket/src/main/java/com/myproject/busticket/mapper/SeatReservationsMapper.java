package com.myproject.busticket.mapper;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.models.Trip;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatReservationsMapper {
    List<SeatReservationsDTO> map(List<SeatReservations> seatReservations);
}
