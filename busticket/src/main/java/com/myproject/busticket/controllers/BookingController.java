package com.myproject.busticket.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Trip;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        for (TripDTO trip : trips) {
            System.out.println("ControllerDTO: " + trip.getNumberOfSeatAvailable());
        }
        model.addAttribute("trips", trips);
        return "search-booking";
    }

    @PostMapping("/booking/testing")
    public String bookingTest(@RequestBody Map<String, Object> booking) {
        Map<String, String> tripDetail = (Map<String, String>) booking.get("tripDetail");
        
        return "redirect:/";
    }

    @PostMapping("/booking/oneway")
    public String booking(@ModelAttribute Booking booking) {
        if (!customerService.hasCustomer(booking.getCustomer().getEmail())){
            customerService.create(booking.getCustomer());
            booking.getCustomer().setCustomerId(customerService.findIDByEmail(booking.getCustomer().getEmail()));
        }
        // set isRoundTrip to false
        bookingService.createTicket(booking);
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
