package com.myproject.busticket.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.TripService;


@RequestMapping("/home/index")
@Controller
public class BookingController {
    BookingService bookingService;
    CustomerService customerService;
    TripService tripService;
    public BookingController(BookingService bookingService, TripService tripService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.tripService = tripService;
        this.customerService = customerService;
    }

    @GetMapping("/search")
    public String searchForm(@RequestParam String tripType, @RequestParam String departure,
                             @RequestParam String destination,
                             @RequestParam String date,
                             @RequestParam String ticketNum,Model model) {
        int numberOfTickets = Integer.parseInt(ticketNum);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateDeparture = LocalDate.parse(date, formatter).atStartOfDay();
        System.out.println(dateDeparture);
        List<TripDTO> trips = tripService.searchTrip(departure, destination, dateDeparture, numberOfTickets);
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

        return "booking";
    }
    @PostMapping("/booking/oneway")
    public String booking(@ModelAttribute Booking booking) {

        return "redirect:/";
    }

    @PostMapping("/booking/roundtrip")
    public String booking(@ModelAttribute Booking outboundTicket, @ModelAttribute Booking returnTicket) {
        if (!customerService.hasCustomer(outboundTicket.getCustomer().getEmail())){
            customerService.create(outboundTicket.getCustomer());
            outboundTicket.getCustomer().setCustomerId(customerService.findIDByEmail(outboundTicket.getCustomer().getEmail()));
        }

        //set isRoundTrip to true
        //set roundTripID
        bookingService.createTicket(outboundTicket);
        bookingService.createTicket(returnTicket);
        return "redirect:/";
    }
}
