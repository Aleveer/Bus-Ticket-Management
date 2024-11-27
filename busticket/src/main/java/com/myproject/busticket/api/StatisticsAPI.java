package com.myproject.busticket.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.services.CustomerService;
import com.myproject.busticket.services.TripService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsAPI {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TripService tripService;

    @GetMapping("/top-customer")
    public List<Object[]> getTopCustomerByBookings(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return customerService.getTopCustomerByBookingsInRange(startDate, endDate, 5);
    }

    @GetMapping("/trip-count-by-route")
    public ResponseEntity<List<Object[]>> countTripsByRouteCode(@RequestParam(required = false) String routeCode) {
        List<Object[]> result = tripService.getTripCountByRouteCode(routeCode);
        return ResponseEntity.ok(result);
    }
}