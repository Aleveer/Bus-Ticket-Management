package com.myproject.busticket.services;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.mapper.SeatReservationsMapper;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.repositories.SeatReservationsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatReservationsService {
    @Autowired
    private SeatReservationsRepository seatReservationsRepository;

    @Autowired
    private Bus_SeatsService bus_SeatsService;

    SeatReservationsMapper seatReservationsMapper = Mappers.getMapper(SeatReservationsMapper.class);

    public SeatReservationsService(SeatReservationsRepository seatReservationsRepository) {
        this.seatReservationsRepository = seatReservationsRepository;
    }

    public List<SeatReservationsDTO> getListStatusSeat(int TripId) {
        List<SeatReservations> seatReservations = seatReservationsRepository.findByTrip_TripId(TripId);
        return seatReservationsMapper.map(seatReservations);
    }

    public List<SeatReservations> getBySeatId(Bus_Seats bus_Seats) {
        return seatReservationsRepository.findBySeat(bus_Seats);
    }

    public boolean isSeatBooked(int seatId, int tripId) {
        List<SeatReservations> seatReservations = seatReservationsRepository.findByTrip_TripId(tripId);
        for (SeatReservations seatReservation : seatReservations) {
            if (seatReservation.getSeat().getId() == seatId) {
                return true;
            }
        }
        return false;
    }

    public boolean isSeatBooked(Bus_Seats seats) {
        List<SeatReservations> seatReservations = seatReservationsRepository.findBySeat(seats);
        return !seatReservations.isEmpty();
    }

    public List<SeatReservations> findByBus(Bus bus) {
        List<Bus_Seats> busSeats = bus_SeatsService.getByBusPlate(bus);
        return busSeats.stream()
                .flatMap(seat -> seatReservationsRepository.findBySeat(seat).stream())
                .collect(Collectors.toList());
    }
}
