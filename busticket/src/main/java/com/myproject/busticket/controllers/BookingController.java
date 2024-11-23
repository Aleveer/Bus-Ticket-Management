package com.myproject.busticket.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myproject.busticket.dto.BookingInfoDTO;
import com.myproject.busticket.dto.SeatReservationsDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Booking;
import com.myproject.busticket.models.SeatReservations;
import com.myproject.busticket.services.BookingService;
import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.RouteCheckpointService;
import com.myproject.busticket.services.SeatReservationsService;
import com.myproject.busticket.services.TripService;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TripService tripService;

    @Autowired
    private RouteCheckpointService routeCheckpointService;

    @Autowired
    private SeatReservationsService seatReservationsService;

    @GetMapping("/home/index/search")
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

    @GetMapping("/home/index/booking/{TripId}")
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
    // @RequestParam("total_price") String totalPrice,
    // @RequestParam("name") String name,
    // @RequestParam("phone") String phone,
    // @RequestParam("email") String email,
    // @PathVariable Integer TripId, Model model) {
    // model.addAttribute("seatIds", seatIds);
    // model.addAttribute("totalPrice", totalPrice);
    // model.addAttribute("name", name);
    // model.addAttribute("phone", phone);
    // model.addAttribute("email", email);
    // model.addAttribute("tripId", tripId);
    // return "payment";
    // }
    @PostMapping("/home/index/booking/oneway")
    public String booking(@RequestBody BookingInfoDTO bookingInfoDTO) {
        System.out.println(bookingInfoDTO);
        return "redirect:/";
    }

    @PostMapping("/home/index/booking/roundtrip")
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

    @GetMapping("/easy-bus/booking-detail/{bookingId}")
    public String getBookingDetail(@PathVariable int bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            model.addAttribute("message", "Booking not found.");
            return "error";
        }

        // For round trips, get the first booking in sequence
        if (booking.isRoundTrip() && booking.getRoundTripId() != null) {
            List<Booking> roundTripBookings = bookingService.findByRoundTripId(booking.getRoundTripId());
            if (!roundTripBookings.isEmpty()) {
                // Get first booking by booking ID (earliest created)
                booking = roundTripBookings.stream()
                        .min(Comparator.comparing(Booking::getBookingId))
                        .orElse(booking);
            }
        }

        model.addAttribute("booking", booking);

        if (booking.getCustomer() == null) {
            model.addAttribute("message", "Customer information is missing.");
            return "error";
        }

        if (booking.getTrip() == null) {
            model.addAttribute("message", "Trip information is missing.");
            return "error";
        }

        if (booking.getTrip().getBus() == null) {
            model.addAttribute("message", "Bus information is missing.");
            return "error";
        }

        if (booking.getTrip().getDriver() == null || booking.getTrip().getDriver().getAccount() == null) {
            model.addAttribute("message", "Driver information is missing.");
            return "error";
        }

        if (booking.getTrip().getController() == null || booking.getTrip().getController().getAccount() == null) {
            model.addAttribute("message", "Controller information is missing.");
            return "error";
        }

        if (booking.getTrip().getStaff() == null || booking.getTrip().getStaff().getAccount() == null) {
            model.addAttribute("message", "Staff information is missing.");
            return "error";
        }

        if (booking.getTrip().getRoute() == null) {
            model.addAttribute("message", "Route information is missing.");
            return "error";
        }

        model.addAttribute("numberOfSeat", booking.getNumberOfSeat());
        model.addAttribute("roundTrip", booking.isRoundTrip());
        model.addAttribute("roundTripId", booking.getRoundTripId());

        List<SeatReservations> seatReservations = seatReservationsService.getByTrip(booking.getTrip());
        if (seatReservations.isEmpty()) {
            model.addAttribute("message", "Seat reservation not found.");
            return "error";
        }

        List<SeatReservations> seatReservationsWithBookingId = seatReservations.stream()
                .filter(seatReservation -> seatReservation.getBooking() != null).collect(Collectors.toList());

        if (seatReservationsWithBookingId.isEmpty()) {
            model.addAttribute("message", "Seat reservation not found.");
            return "error";
        }

        List<Map<String, Object>> seatDetails = seatReservationsWithBookingId.stream().map(seatReservation -> {
            Map<String, Object> seatMap = new HashMap<>();
            seatMap.put("seatId", seatReservation.getSeat().getId());
            seatMap.put("seatNumber", seatReservation.getSeat().getSeatName());
            seatMap.put("status", seatReservation.getStatus().toString());
            seatMap.put("bookingId", seatReservation.getBooking().getBookingId());
            return seatMap;
        }).collect(Collectors.toList());

        model.addAttribute("seatDetails", seatDetails);

        if (booking.isRoundTrip()) {
            model.addAttribute("roundTrip", true);
            String roundTripId = booking.getRoundTripId();
            List<Booking> roundTripBookings = bookingService.findByRoundTripId(roundTripId);
            if (roundTripBookings.isEmpty()) {
                model.addAttribute("message", "Round trip booking not found.");
                return "error";
            }
            final Booking finalBooking = booking;
            // Get the return trip (second booking in sequence)
            Booking roundTripBooking = roundTripBookings.stream()
                    .filter(b -> b.getBookingId() != finalBooking.getBookingId())
                    .min(Comparator.comparing(Booking::getBookingId))
                    .orElse(null);

            if (roundTripBooking == null) {
                model.addAttribute("message", "Return trip booking not found.");
                return "error";
            }

            model.addAttribute("roundTrip", roundTripBooking);

            List<SeatReservations> roundTripSeatReservations = seatReservationsService
                    .getByTrip(roundTripBooking.getTrip());
            if (roundTripSeatReservations.isEmpty()) {
                model.addAttribute("message", "Seat reservation not found for round trip.");
                return "error";
            }

            List<SeatReservations> roundTripSeatReservationsWithBookingId = roundTripSeatReservations.stream()
                    .filter(seatReservation -> seatReservation.getBooking() != null).collect(Collectors.toList());

            if (roundTripSeatReservationsWithBookingId.isEmpty()) {
                model.addAttribute("message", "Seat reservation not found for round trip.");
                return "error";
            }

            List<Map<String, Object>> roundTripSeatDetails = roundTripSeatReservationsWithBookingId.stream()
                    .map(seatReservation -> {
                        Map<String, Object> seatMap = new HashMap<>();
                        seatMap.put("seatId", seatReservation.getSeat().getId());
                        seatMap.put("seatNumber", seatReservation.getSeat().getSeatName());
                        seatMap.put("status", seatReservation.getStatus().toString());
                        seatMap.put("bookingId", seatReservation.getBooking().getBookingId());
                        return seatMap;
                    }).collect(Collectors.toList());
            model.addAttribute("roundTripSeatDetails", roundTripSeatDetails);
            model.addAttribute("tripId", roundTripBooking.getTrip().getTripId());
            model.addAttribute("departureTime", roundTripBooking.getTrip().getDepartureTime());
            model.addAttribute("arrivalTime", roundTripBooking.getTrip().getArrivalTime());
            model.addAttribute("price", roundTripBooking.getTrip().getPrice());
            model.addAttribute("status", roundTripBooking.getTrip().getStatus());
        }

        return "booking-detail";
    }

    @GetMapping("/easy-bus/new-booking")
    public String newBooking(Model model) {
        model.addAttribute("booking", new Booking());
        return "new-booking";
    }

    @PostMapping("/easy-bus/new-booking")
    public String saveBooking(Booking booking) {
        bookingService.createTicket(booking);
        return "redirect:/easy-bus/booking-management";
    }
}
