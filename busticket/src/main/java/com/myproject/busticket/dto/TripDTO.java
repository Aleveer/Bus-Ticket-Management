package com.myproject.busticket.dto;

import com.myproject.busticket.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class TripDTO {
    private int tripId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private float price;
    private TripStatus status;

    private BusDTO bus;
    private DriverDTO driver;
    private ControllerDTO controller;
    private StaffDTO staff;
    private RouteDTO route;

    private int numberOfSeatAvailable;
    public TripDTO(){}

    public TripDTO(int TripId, LocalDateTime departureTime, LocalDateTime arrivalTime, float price, TripStatus status, BusDTO bus, DriverDTO driver, ControllerDTO controller, StaffDTO staff, RouteDTO route) {
        this.tripId = TripId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.status = status;
        this.bus = bus;
        this.driver = driver;
        this.controller = controller;
        this.staff = staff;
        this.route = route;
    }
}
