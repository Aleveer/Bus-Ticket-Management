package com.myproject.busticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private int routeId;
    private String code;
    private String name;
    private LocalDateTime time;
    private double distance;
}