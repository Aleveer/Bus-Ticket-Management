package com.myproject.busticket.dto;

import java.util.List;

public class BookingInfoDTO {
    private CustomerTicketDTO customer;
    private int tripId;
    private byte numberOfSeat;
    private List<String> seatNumbers;
    private double price;
}
