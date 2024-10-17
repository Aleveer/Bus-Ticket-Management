package com.myproject.busticket.models;

import com.myproject.busticket.enums.BusStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "buses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private Account user;

    @Column(name = "bus_type", nullable = false)
    private String busType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "enum('working', 'maintenance') default 'working'")
    private BusStatus status;
}