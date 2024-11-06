package com.myproject.busticket.mapper;

import com.myproject.busticket.dto.DriverDTO;
import com.myproject.busticket.dto.TripDTO;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.models.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    TripDTO entityToDTO(Trip trip);
    List<TripDTO> map(List<Trip> trips);
    DriverDTO entityToDTO(Driver driver);
}
