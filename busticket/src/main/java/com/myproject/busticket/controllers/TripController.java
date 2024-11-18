package com.myproject.busticket.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Bus_Seats;
import com.myproject.busticket.models.Feedback;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.BusService;
import com.myproject.busticket.services.Bus_SeatsService;
import com.myproject.busticket.services.FeedbackService;
import com.myproject.busticket.services.SeatReservationsService;
import com.myproject.busticket.services.TripService;

@Controller
public class TripController {
    @Autowired
    private TripService tripService;

    @Autowired
    private Bus_SeatsService bus_SeatsService;

    @Autowired
    private BusService busService;

    @Autowired
    private SeatReservationsService seatReservationService;

    @Autowired
    private FeedbackService feedbackService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/api/trip/{tripId}")
    public TripDTO getById(@PathVariable int tripId) {
        TripDTO trip = tripService.findById(tripId);
        return trip;
    }

    @GetMapping("/api/trips")
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @GetMapping("admin/trip-detail/{tripId}")
    public String getTripDetails(@PathVariable int tripId, @RequestParam(defaultValue = "1") int page, Model model) {
        TripDTO trip = tripService.findById(tripId);
        if (trip == null) {
            model.addAttribute("errorMessage", "Trip not found.");
            return "redirect:/admin/trip-management";
        }

        Bus existingBus = busService.getByBusPlate(trip.getBus().getBusId());

        List<Bus_Seats> seats = bus_SeatsService.getByBusPlate(existingBus);
        List<Map<String, Object>> seatDetails = seats.stream()
                .map(seat -> {
                    Map<String, Object> seatMap = new HashMap<>();
                    seatMap.put("seatName", seat.getSeatName());
                    seatMap.put("status", seatReservationService.getStatusBySeatAndTrip(seat, trip));
                    return seatMap;
                })
                .collect(Collectors.toList());

        Page<Feedback> feedbackPage = feedbackService.findByTripDTO(trip, PageRequest.of(page - 1, 3));
        List<Feedback> feedbacks = feedbackPage.getContent();

        model.addAttribute("trip", trip);
        model.addAttribute("seatDetails", seatDetails);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", feedbackPage.getTotalPages());

        return "trip-detail";
    }
}
