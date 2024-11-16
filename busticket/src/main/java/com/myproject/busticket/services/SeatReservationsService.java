package com.myproject.busticket.services;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.mapper.SeatReservationsMapper;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.repositories.SeatReservationsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatReservationsService {
    SeatReservationsRepository seatReservationsRepository;
    SeatReservationsMapper seatReservationsMapper = Mappers.getMapper(SeatReservationsMapper.class);

    public SeatReservationsService(SeatReservationsRepository seatReservationsRepository) {
        this.seatReservationsRepository = seatReservationsRepository;
    }

    public List<SeatReservationsDTO> getListStatusSeat(int TripId) {
        List<SeatReservations> seatReservations = seatReservationsRepository.findByTrip_TripId(TripId);
        return seatReservationsMapper.map(seatReservations);
    }
}
