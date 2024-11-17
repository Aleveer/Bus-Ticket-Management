package com.myproject.busticket.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.Bus_SeatsService;
import com.myproject.busticket.services.SeatReservationsService;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.SeatReservations;

@Controller
@RequestMapping("/admin")
public class BusController {
    @Autowired
    private BusService busService;

    @Autowired
    private Bus_SeatsService bus_SeatsService;

    @Autowired
    private SeatReservationsService seatReservationService;

    @GetMapping("/new-bus")
    public String newBus(Model model) {
        model.addAttribute("bus", new Bus());
        return "new-bus";
    }

    @PostMapping("/new-bus")
    public String saveBus(Bus bus) {
        busService.save(bus);
        return "redirect:/admin/bus-management";
    }

    @GetMapping("/bus-detail/{plate}")
    public String getBusDetails(@PathVariable String plate, Model model) {
        Bus bus = busService.getByBusPlate(plate);
        if (bus == null) {
            model.addAttribute("errorMessage", "Bus not found.");
            return "redirect:/admin/bus-management";
        }

        List<Bus_Seats> seats = bus_SeatsService.getByBusPlate(bus);
        List<Map<String, Object>> seatDetails = seats.stream()
                .map(seat -> {
                    Map<String, Object> seatMap = new HashMap<>();
                    seatMap.put("seatName", seat.getSeatName());
                    seatMap.put("booked", seatReservationService.isSeatBooked(seat));
                    return seatMap;
                })
                .collect(Collectors.toList());

        List<SeatReservations> reservations = seatReservationService.findByBus(bus);
        List<Map<String, Object>> reservationDetails = reservations.stream()
                .map(reservation -> {
                    Map<String, Object> reservationMap = new HashMap<>();
                    reservationMap.put("seatName", reservation.getSeat().getSeatName());
                    reservationMap.put("customerName", reservation.getBooking().getCustomer().getName());
                    reservationMap.put("customerEmail", reservation.getBooking().getCustomer().getEmail());
                    reservationMap.put("trip", reservation.getTrip().getTripId());
                    reservationMap.put("status", reservation.getStatus().name());
                    return reservationMap;
                })
                .collect(Collectors.toList());

        model.addAttribute("bus", bus);
        model.addAttribute("seatDetails", seatDetails);
        model.addAttribute("reservationDetails", reservationDetails);

        return "bus-detail";
    }
}
