package com.myproject.busticket.services;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.mapper.SeatReservationsMapper;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.SeatReservationsRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<SeatReservations> getBySeat(Bus_Seats bus_Seats) {
        return seatReservationsRepository.findBySeat(bus_Seats);
    }

    public SeatReservations save(SeatReservations seatReservations) {
        return seatReservationsRepository.save(seatReservations);
    }

    // public boolean isSeatBooked(int seatId, int tripId) {
    // List<SeatReservations> seatReservations =
    // seatReservationsRepository.findByTrip_TripId(tripId);
    // for (SeatReservations seatReservation : seatReservations) {
    // if (seatReservation.getSeat().getId() == seatId) {
    // return true;
    // }
    // }
    // return false;
    // }

    // public boolean isSeatBooked(Bus_Seats seats) {
    // List<SeatReservations> seatReservations =
    // seatReservationsRepository.findBySeat(seats);
    // for (SeatReservations seatReservation : seatReservations) {
    // if (seatReservation.getStatus().equals(SeatReservationStatus.booked)) {
    // return true;
    // }
    // }
    // return false;
    // }

    // public List<SeatReservations> findByBus(Bus bus) {
    // List<Bus_Seats> busSeats = bus_SeatsService.getByBusPlate(bus);
    // return busSeats.stream()
    // .flatMap(seat -> seatReservationsRepository.findBySeat(seat).stream())
    // .collect(Collectors.toList());
    // }

    public SeatReservations getReservationBySeatAndTrip(Bus_Seats seat, Trip trip) {
        List<SeatReservations> seatReservations = seatReservationsRepository.findBySeatIdAndTripId(seat.getId(),
                trip.getTripId());
        if (seatReservations.size() > 0) {
            return seatReservations.get(0);
        }
        return null;
    }
}
