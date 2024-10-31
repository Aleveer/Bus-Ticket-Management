package com.myproject.busticket.dto;

import com.myproject.busticket.enums.BusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private String busId;
    private int numberOfSeats;
    private BusType type;
}
