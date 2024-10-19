package com.myproject.busticket.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.myproject.busticket.enums.TripStatus;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "price", nullable = false)
    private float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TripStatus status;

    @Column(name = "bus_id", nullable = false)
    private String busId;

    @Column(name = "driver_id", nullable = false)
    private int driverId;

    @Column(name = "controller_id", nullable = false)
    private int controllerId;

    @Column(name = "staff_id", nullable = false)
    private int staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_code", referencedColumnName = "code", nullable = false)
    private Route route;
}