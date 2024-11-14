package com.myproject.busticket.dto;

import com.myproject.busticket.models.Customer;
import com.myproject.busticket.models.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private int bookingId;
    private Customer customer;
    private Trip trip;
    private byte numberOfSeat;
    private boolean isRoundTrip;
    private String roundTripId;
}
