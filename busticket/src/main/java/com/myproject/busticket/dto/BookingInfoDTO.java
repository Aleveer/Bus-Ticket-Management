package com.myproject.busticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingInfoDTO {
    @JsonProperty("cusDetail")
    private CustomerTicketDTO customer;

    @JsonProperty("tripDetail")
    private TicketInfoDTO ticketInfoDTO;
}
