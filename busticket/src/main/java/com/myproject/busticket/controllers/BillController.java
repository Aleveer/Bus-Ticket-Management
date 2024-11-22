package com.myproject.busticket.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myproject.busticket.dto.BillDetailDTO;
import com.myproject.busticket.mapper.BillMapper;
import com.myproject.busticket.mapper.TripMapper;
import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.services.BillDetailService;
import com.myproject.busticket.services.BillService;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.TripService;

@Controller
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TripService tripService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private TripMapper tripMapper;

    @GetMapping("/easy-bus/bill-detail/{billId}")
    public String getBillDetails(@PathVariable int billId, Model model) {
        Bill bill = billService.findById(billId);
        if (bill == null) {
            model.addAttribute("errorMessage", "Bill not found.");
            return "redirect:/easy-bus/bill-management";
        }

        Customer customer = customerService.getCustomerById(bill.getCustomer().getCustomerId());
        if (customer == null) {
            model.addAttribute("errorMessage", "Customer not found.");
            return "redirect:/easy-bus/bill-management";
        }

        List<Bill_Detail> billDetails = billDetailService.findByBillId(bill);
        if (billDetails == null || billDetails.isEmpty()) {
            model.addAttribute("errorMessage", "Bill details not found.");
            return "redirect:/easy-bus/bill-management";
        }

        Trip trip = tripService.findTripById(billDetails.get(0).getTrip().getTripId());
        if (trip == null) {
            model.addAttribute("errorMessage", "Trip not found.");
            return "redirect:/easy-bus/bill-management";
        }

        List<BillDetailDTO> billDetailsDTO = billDetails.stream()
                .map(billDetail -> new BillDetailDTO(
                        billDetail.getId(),
                        billMapper.entityToDTO(billDetail.getBill()),
                        tripMapper.entityToDTO(billDetail.getTrip()),
                        billDetail.getNumberOfTicket(),
                        billDetail.getFee(),
                        billDetail.getTicketType()))
                .collect(Collectors.toList());

        // Retrieve bookings for the customer and trip
        List<Booking> bookings = bookingService.findByCustomer(customer);
        Booking booking = null;
        Trip roundTrip = null;

        bookings = bookings.stream().filter(b -> b.getTrip().getTripId() == trip.getTripId())
                .collect(Collectors.toList());

        // check bookings for round trip
        if (bookings.size() == 1) {
            booking = bookings.get(0);
            if (booking.isRoundTrip() && booking.getRoundTripId() != null) {
                List<Booking> roundTripBookings = bookingService.findByRoundTripId(booking.getRoundTripId());
                if (!roundTripBookings.isEmpty()) {
                    roundTrip = roundTripBookings.get(0).getTrip();
                }
            }
        } else if (!bookings.isEmpty()) {
            roundTrip = bookings.get(bookings.size() - 1).getTrip();
        }

        model.addAttribute("bill", bill);
        model.addAttribute("customer", customer);
        model.addAttribute("trip", trip);
        model.addAttribute("roundTrip", roundTrip);
        model.addAttribute("billDetails", billDetailsDTO);
        // model.addAttribute("bookings", bookings);
        model.addAttribute("booking", booking);

        return "bill-detail";
    }
}
