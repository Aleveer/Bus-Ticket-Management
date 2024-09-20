package com.myproject.busticket.models;

import com.myproject.busticket.enums.BookingStatus;
import com.myproject.busticket.enums.TicketType;

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
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "number_of_tickets", nullable = false)
    private int numberOfTickets;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "enum('paid', 'unpaid', 'canceled') default 'unpaid'")
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false, columnDefinition = "enum('round_trip_ticket', 'one_way_ticket') default 'one_way_ticket'")
    private TicketType ticketType;
}