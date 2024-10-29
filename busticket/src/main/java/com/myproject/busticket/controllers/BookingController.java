package com.myproject.busticket.controllers;

import com.myproject.busticket.models.Booking;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/booking")
@Controller
public class BookingController {
    BookingService bookingService;
    CustomerService customerService;

    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @PostMapping("/oneway")
    public String booking(@ModelAttribute Booking booking) {
        if (!customerService.hasCustomer(booking.getCustomer().getEmail())) {
            customerService.create(booking.getCustomer());
            booking.getCustomer().setCustomerId(customerService.findIDByEmail(booking.getCustomer().getEmail()));
        }
        // set isRoundTrip to false
        bookingService.createTicket(booking);
        return "redirect:/";
    }

    @PostMapping("/roundtrip")
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
