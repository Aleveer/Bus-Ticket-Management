package com.myproject.busticket.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.SeatReservationsService;
import com.myproject.busticket.services.TripService;

@RequestMapping("/home/index")
@Controller
public class BookingController {
    BookingService bookingService;
    CustomerService customerService;
    TripService tripService;
    RouteCheckpointService routeCheckpointService;

    SeatReservationsService seatReservationsService;

    public BookingController(BookingService bookingService, TripService tripService, CustomerService customerService,
            SeatReservationsService seatReservationsService, RouteCheckpointService routeCheckpointService) {
        this.bookingService = bookingService;
        this.tripService = tripService;
        this.customerService = customerService;
        this.seatReservationsService = seatReservationsService;
        this.routeCheckpointService = routeCheckpointService;
    }

    @GetMapping("/search")
    public String searchForm(@RequestParam String tripType, @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String date,
            @RequestParam String ticketNum, Model model) {
        int numberOfTickets = Integer.parseInt(ticketNum);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateDeparture = LocalDate.parse(date, formatter).atStartOfDay();
        List<TripDTO> trips = tripService.searchTrip(departure, destination, dateDeparture, numberOfTickets);
        
        List<String> provinces = routeCheckpointService.getAllProvinces();
        List<String> cities = routeCheckpointService.getAllCities();
        model.addAttribute("tripType", "one-way");
        model.addAttribute("provinces", provinces);
        model.addAttribute("cities", cities);

        model.addAttribute("trips", trips);
        model.addAttribute("tripType", tripType);
        model.addAttribute("departure", departure);
        model.addAttribute("destination", destination);
        model.addAttribute("date", date);
        model.addAttribute("ticketNum", ticketNum);
        return "search-booking";
    }

    @GetMapping("/booking/{TripId}")
    public String chooseInfo(@PathVariable Integer TripId, Model model) {
        TripDTO selectedTrip = tripService.findById(TripId);
        System.out.println(selectedTrip.getBus().getSeatType());
        List<SeatReservationsDTO> statusSeats = seatReservationsService.getListStatusSeat(TripId);
        List<SeatReservationsDTO> firstFloor = statusSeats.subList(0, statusSeats.size() / 2);
        List<SeatReservationsDTO> secondFloor = statusSeats.subList(statusSeats.size() / 2, statusSeats.size());
        model.addAttribute("selectedTrip", selectedTrip);
        model.addAttribute("firstFloor", firstFloor);
        model.addAttribute("secondFloor", secondFloor);
        model.addAttribute("seatType", selectedTrip.getBus().getSeatType().toString().trim());
        return "booking";
    }
    // @GetMapping("/booking/payment/{TripId}")
    // public String payment(@RequestParam("seat_ids") String seatIds,
    //         @RequestParam("total_price") String totalPrice,
    //         @RequestParam("name") String name,
    //         @RequestParam("phone") String phone,
    //         @RequestParam("email") String email,
    //         @PathVariable Integer TripId, Model model) {
        // model.addAttribute("seatIds", seatIds);
        // model.addAttribute("totalPrice", totalPrice);
        // model.addAttribute("name", name);
        // model.addAttribute("phone", phone);
        // model.addAttribute("email", email);
        // model.addAttribute("tripId", tripId);     
    //     return "payment";
    // }
    @PostMapping("/booking/oneway")
    public String booking(@ModelAttribute Booking booking) {

        return "redirect:/";
    }

    @PostMapping("/booking/roundtrip")
    public String booking(@ModelAttribute Booking outboundTicket, @ModelAttribute Booking returnTicket) {
        if (!customerService.hasCustomer(outboundTicket.getCustomer().getEmail())) {
            customerService.create(outboundTicket.getCustomer());
            outboundTicket.getCustomer()
                    .setCustomerId(customerService.findIDByEmail(outboundTicket.getCustomer().getEmail()));
        }

        // set isRoundTrip to true
        // set roundTripID
        bookingService.createTicket(outboundTicket);
        bookingService.createTicket(returnTicket);
        return "redirect:/";
    }

}
