package com.myproject.busticket.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/search")
    public String searchForm(@RequestParam String tripType, @RequestParam String departure,
                             @RequestParam String destination,
                             @RequestParam String date,
                             @RequestParam int ticketNum,Model model) {
                            model.addAttribute("tripType", tripType);
                            model.addAttribute("departure", departure);
                            model.addAttribute("destination", destination);
                            model.addAttribute("date", date);
                            model.addAttribute("ticketNum", ticketNum);
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
