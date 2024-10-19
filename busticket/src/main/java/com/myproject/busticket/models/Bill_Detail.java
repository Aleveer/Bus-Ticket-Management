package com.myproject.busticket.models;

import com.myproject.busticket.enums.TicketType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bill_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "number_of_ticket", nullable = false)
    private short numberOfTicket;

    @Column(name = "fee", nullable = false)
    private float fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false)
    private TicketType ticketType;

}