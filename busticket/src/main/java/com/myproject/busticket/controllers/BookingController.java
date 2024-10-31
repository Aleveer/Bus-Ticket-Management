package com.myproject.busticket.controllers;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/booking")
    public String booking(Model model) {
        List<Trip> list = tripService.findAll();
        for (Trip trip : list) {
            System.out.println(trip.getTripId());
        }
        model.addAttribute("trips", list);
        return "bookingTest";
    }

    @PostMapping("/booking")
    public String searchForm(Model model) {
        return "booking";
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
